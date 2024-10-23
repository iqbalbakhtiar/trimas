package com.siriuserp.sdk.utility;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectPrinter {

    private static final int INDENTATION_SIZE = 2;

    // Field default untuk dicetak dalam nested object
    private static final List<String> DEFAULT_INCLUDED_FIELDS =
            Arrays.asList("id", "name", "code", "fullName");

    // Field yang akan di-exclude dari output
    private static final Set<String> EXCLUDED_FIELDS = new HashSet<>(
            Arrays.asList("form")  // Tambahkan field yang ingin di-exclude di sini
    );

    public static void printJson(Object obj) {
        System.out.println(toJson(obj));
    }

    private static String toJson(Object obj) {
        StringBuilder sb = new StringBuilder();
        try {
            toJson(obj, sb, new HashSet<>(), 0);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void toJson(Object obj, StringBuilder sb, Set<Object> visited, int indentLevel) throws IllegalAccessException {
        if (obj == null) {
            sb.append("null");
            return;
        }

        if (visited.contains(obj)) {
            sb.append("\"<circular reference>\"");
            return;
        }
        visited.add(obj);

        obj = initializeAndUnproxy(obj);  // Handle Lazy Loading

        Class<?> clazz = obj.getClass();

        if (isPrimitiveOrWrapper(clazz) || clazz.equals(String.class)) {
            sb.append("\"").append(obj.toString()).append("\"");
            return;
        }

        if (clazz.equals(Date.class)) {
            sb.append("\"").append(formatDate((Date) obj)).append("\"");
            return;
        }

        if (obj instanceof Collection) {
            sb.append("[");
            Collection<?> collection = (Collection<?>) obj;
            boolean firstElement = true;
            for (Object element : collection) {
                if (!firstElement) {
                    sb.append(",\n").append(indent(indentLevel + 1));
                } else {
                    sb.append("\n").append(indent(indentLevel + 1));
                    firstElement = false;
                }
                sb.append("\"").append(element.toString()).append("\"");
            }
            if (!collection.isEmpty()) {
                sb.append("\n").append(indent(indentLevel));
            }
            sb.append("]");
            return;
        }

        sb.append("{");
        Field[] fields = getAllFields(clazz);
        boolean firstField = true;
        for (Field field : fields) {
            // Lewati field yang ada di daftar EXCLUDED_FIELDS
            if (EXCLUDED_FIELDS.contains(field.getName())) {
                continue;
            }

            int modifiers = field.getModifiers();
            if (Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers)) {
                continue; // Lewati field final dan static
            }

            if (!Modifier.isPublic(modifiers)) {
                field.setAccessible(true);
            }

            Object value = field.get(obj);

            if (value == null) {
                continue; // Lewati nilai null
            }

            if (firstField) {
                sb.append("\n");
                firstField = false;
            } else {
                sb.append(",\n");
            }

            sb.append(indent(indentLevel + 1));
            sb.append("\"").append(field.getName()).append("\": ");

            Class<?> fieldType = field.getType();

            if (isPrimitiveOrWrapper(fieldType) || fieldType.equals(String.class)) {
                sb.append("\"").append(value.toString()).append("\"");
            } else if (fieldType.equals(Date.class)) {
                sb.append("\"").append(formatDate((Date) value)).append("\"");
            } else if (value instanceof Collection) {
                sb.append("[");
                Collection<?> collection = (Collection<?>) value;
                boolean firstElementInCollection = true;
                for (Object element : collection) {
                    if (!firstElementInCollection) {
                        sb.append(",\n").append(indent(indentLevel + 2));
                    } else {
                        sb.append("\n").append(indent(indentLevel + 2));
                        firstElementInCollection = false;
                    }
                    sb.append("\"").append(element.toString()).append("\"");
                }
                if (!collection.isEmpty()) {
                    sb.append("\n").append(indent(indentLevel + 1));
                }
                sb.append("]");
            } else {
                // Untuk objek lain, hanya cetak id, name, dan code
                appendIdNameCode(value, sb, visited, indentLevel + 1);
            }
        }
        if (!firstField) {
            sb.append("\n").append(indent(indentLevel));
        }
        sb.append("}");
    }

    private static void appendIdNameCode(Object obj, StringBuilder sb, Set<Object> visited, int indentLevel) throws IllegalAccessException {
        if (obj == null) {
            sb.append("null");
            return;
        }
        if (visited.contains(obj)) {
            sb.append("\"<circular reference>\"");
            return;
        }
        visited.add(obj);

        obj = initializeAndUnproxy(obj);

        sb.append("{");
        boolean firstField = true;
        for (String fieldName : DEFAULT_INCLUDED_FIELDS) {
            Field field = getFieldFromClassHierarchy(obj.getClass(), fieldName);
            if (field != null) {
                if (!Modifier.isPublic(field.getModifiers())) {
                    field.setAccessible(true);
                }
                Object value = field.get(obj);
                if (firstField) {
                    sb.append("\n");
                    firstField = false;
                } else {
                    sb.append(",\n");
                }
                sb.append(indent(indentLevel + 1));
                sb.append("\"").append(fieldName).append("\": ");
                if (value != null) {
                    sb.append("\"").append(value.toString()).append("\"");
                } else {
                    sb.append("null");
                }
            }
        }
        if (firstField) {
            sb.append("\n").append(indent(indentLevel + 1));
            sb.append("\"<No relevant fields>\": \"").append(obj.toString()).append("\"");
        } else {
            sb.append("\n").append(indent(indentLevel));
        }
        sb.append("}");
    }

    private static Object initializeAndUnproxy(Object entity) {
        if (entity == null) {
            return null;
        }
        if (entity instanceof HibernateProxy) {
            Hibernate.initialize(entity);
            entity = ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
        }
        return entity;
    }

    private static Field[] getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && !current.equals(Object.class)) {
            fields.addAll(Arrays.asList(current.getDeclaredFields()));
            current = current.getSuperclass();
        }
        return fields.toArray(new Field[0]);
    }

    private static Field getFieldFromClassHierarchy(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null && !current.equals(Object.class)) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }

    private static String indent(int level) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level * INDENTATION_SIZE; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        return type.isPrimitive() ||
                type.equals(Boolean.class) ||
                type.equals(Byte.class) ||
                type.equals(Character.class) ||
                type.equals(Short.class) ||
                type.equals(Integer.class) ||
                type.equals(Long.class) ||
                type.equals(Float.class) ||
                type.equals(Double.class);
    }

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }
}
