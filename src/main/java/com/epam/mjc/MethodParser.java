package com.epam.mjc;

import java.util.ArrayList;
import java.util.List;

public class MethodParser {

    private static final int SIGNATURE_WITHOUT_ACCESS_MODIFIER_LENGTH = 2;
    private static final int SIGNATURE_WITH_ACCESS_MODIFIER_LENGTH = 3;
    private static final int ARGUMENT_PARTS_LENGTH = 2;

    /**
     * Parses string that represents a method signature and stores all it's members into a {@link MethodSignature} object.
     * signatureString is a java-like method signature with following parts:
     * 1. access modifier - optional, followed by space: ' '
     * 2. return type - followed by space: ' '
     * 3. method name
     * 4. arguments - surrounded with braces: '()' and separated by commas: ','
     * Each argument consists of argument type and argument name, separated by space: ' '.
     * Examples:
     * accessModifier returnType methodName(argumentType1 argumentName1, argumentType2 argumentName2)
     * private void log(String value)
     * Vector3 distort(int x, int y, int z, float magnitude)
     * public DateTime getCurrentDateTime()
     *
     * @param signatureString source string to parse
     * @return {@link MethodSignature} object filled with parsed values from source string
     */
    public MethodSignature parseFunction(String signatureString) {
        // split into two parts - descriptions of the method and the arguments
        String[] token = signatureString.split("[\\(\\)]");
        String argumentsPart = "";
        if (token.length > 1) {
            // argument exists
            argumentsPart = token[1].trim();
        }
        List<MethodSignature.Argument> arguments = parseArgument(argumentsPart);
        MethodSignature result = parseMethod(token[0], arguments);
        return result;
    }

    private List<MethodSignature.Argument> parseArgument(String description) {
        List<MethodSignature.Argument> result = new ArrayList<>();
        if (description.length() > 0) {
            String[] token = description.trim().split(",");
            for (String argument : token) {
                String[] parts = argument.trim().split(" ");
                if (parts.length != ARGUMENT_PARTS_LENGTH) {
                    throw new IllegalArgumentException("Invalid the argument description: " + argument);
                }
                result.add(new MethodSignature.Argument(parts[0], parts[1]));
            }
        }
        return result;
    }

    private MethodSignature parseMethod(String description, List<MethodSignature.Argument> arguments) {
        String[] token = description.split(" ");
        if (token.length < SIGNATURE_WITHOUT_ACCESS_MODIFIER_LENGTH
                || token.length > SIGNATURE_WITH_ACCESS_MODIFIER_LENGTH) {
            throw new IllegalArgumentException("Invalid the method signature: " + description);
        }
        String methodName = token[token.length - 1];
        MethodSignature result = new MethodSignature(methodName, arguments);
        String returnType = token[token.length - 2];
        result.setReturnType(returnType);
        if (token.length == SIGNATURE_WITH_ACCESS_MODIFIER_LENGTH) {
            // signature with access modifier
            String modifier = token[0];
            result.setAccessModifier(modifier);
        }
        return result;
    }
}
