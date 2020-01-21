package com.fresh.dbfinjector.services;

import lombok.extern.slf4j.Slf4j;
import nl.knaw.dans.common.dbflib.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
public class DBFTesting {


    @Test
    public void findEmp() {
        final Table table = new Table(new File("emp.dbf"));
        try {
            table.open(IfNonExistent.ERROR);
            final List<Field> fields = table.getFields();

            final Iterator<Record> recordIterator = table.recordIterator();
            int count = 0;

            while (recordIterator.hasNext()) {
                final Record record = recordIterator.next();
                if (record.getNumberValue("ID").intValue() == 999) {
                    for (final Field field : fields) {
                        try {
                            byte[] rawValue = record.getRawValue(field);
                            System.out.println(field.getName() + " : " + (rawValue == null ? "<NULL>" : new String(rawValue)));
                        } catch (ValueTooLargeException vtle) {
                            // Cannot happen :)
                        } catch (DbfLibException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (CorruptedTableException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException ex) {
                System.out.println("Unable to close the table");
            }
        }
    }

    @Test
    public void updateEmp() {
        final Table table = new Table(new File("emp.dbf"));
        try {
            table.open(IfNonExistent.ERROR);
            final List<Field> fields = table.getFields();

            for (int i = 0; i < table.getRecordCount(); i++) {
                final Record record = table.getRecordAt(i);
                if (record.isMarkedDeleted()) continue;
                if (record.getNumberValue("ID").intValue() == 999) {
                    Map<String, Value> valueMap = new HashMap<>();

                    for (final Field field : fields) {
//                        valueMap.put(field.getName(), createValueObject(record.getTypedValue(field.getName()), table.getCharsetName()));
                        valueMap.put(field.getName(), new ByteArrayValue(record.getRawValue(field)));

//
                    }
                    valueMap.put("OWNERID", new NumberValue(1));

                    Record newRecord = new Record(valueMap);
                    log.info("" + i);

                    table.updateRecordAt(i, newRecord);
                }
            }


        } catch (IOException | DbfLibException e) {

            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException ex) {
                System.out.println("Unable to close the table");
            }
        }

    }

    @Test
    public void updateEmpNew() {
        final Table table = new Table(new File("emp.dbf"));
        try {
            table.open(IfNonExistent.ERROR);
            final List<Field> fields = table.getFields();

            for (int i = 0; i < table.getRecordCount(); i++) {
                final Record record = table.getRecordAt(i);
                if (record.isMarkedDeleted()) continue;
                if (record.getNumberValue("ID").intValue() == 109) {
                    Map<String, Value> valueMap = new HashMap<>();

                    valueMap.put("OWNERID", new StringValue("" + 1));
                    valueMap.put("FIRSTNAME", new StringValue("Ronnie"));

                    Record newRecord = new Record(valueMap);
                    log.info("" + i);

                    table.updateRecordAt(i, newRecord, false);
                }
            }


        } catch (IOException | DbfLibException e) {

            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException ex) {
                System.out.println("Unable to close the table");
            }
        }

    }

    private Value createValueObject(final Object value, String charsetName) {
        if (value instanceof Number) {
            return new NumberValue((Number) value);
        } else if (value instanceof String) {
            return new StringValue((String) value, charsetName);
        } else if (value instanceof Boolean) {
            return new BooleanValue((Boolean) value);
        } else if (value instanceof Date) {
            return new DateValue((Date) value);
        } else if (value instanceof byte[]) {
            return new ByteArrayValue((byte[]) value);
        }

        return null;
    }

    @Test
    public void fieldTest() {
        final Table table = new Table(new File("emp.dbf"));
        try {
            table.open(IfNonExistent.ERROR);
            final List<Field> fields = table.getFields();
            for (final Field field : fields) {
                System.out.println(field.getLength());
            }

        } catch (IOException | DbfLibException e) {

            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException ex) {
                System.out.println("Unable to close the table");
            }
        }
    }

    @Test
    public void rawTest() {
        final Table table = new Table(new File("emp.dbf"));
        try {
            table.open(IfNonExistent.ERROR);
            final List<Field> fields = table.getFields();

            final Iterator<Record> recordIterator = table.recordIterator();
            int count = 0;

            while (recordIterator.hasNext()) {
                final Record record = recordIterator.next();
                if (record.getNumberValue("ID").intValue() == 999) {
                    for (final Field field : fields) {
                        try {
                            byte[] rawValue = record.getRawValue(field);
                            System.out.println(field.getName() + " : " + (rawValue == null ? "<NULL>" : new String(rawValue)));
                        } catch (ValueTooLargeException vtle) {
                            // Cannot happen :)
                        } catch (DbfLibException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (CorruptedTableException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                table.close();
            } catch (IOException ex) {
                System.out.println("Unable to close the table");
            }
        }
    }
}
