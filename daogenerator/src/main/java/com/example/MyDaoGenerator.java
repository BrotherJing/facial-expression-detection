package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.greendao.dao");

        addTag(schema);

        new DaoGenerator().generateAll(schema, "./app/src/main/java");
    }

    private static void addTag(Schema schema){
        Entity activity = schema.addEntity("Tag");
        activity.addIdProperty().autoincrement().primaryKey();
        activity.addStringProperty("name");
        activity.addIntProperty("count");
    }

    private static void addActivityAlarm(Schema schema){
        Entity alarm = schema.addEntity("Alarm");
        alarm.addIdProperty().autoincrement().primaryKey();
        alarm.addLongProperty("alarm_id");
        alarm.addStringProperty("title");
        alarm.addDateProperty("start_time");
        alarm.addDateProperty("ring_time");
    }

    private static void addBillInfo(Schema schema){
        Entity billInfo = schema.addEntity("BillInfo");
        billInfo.addIdProperty().autoincrement().primaryKey();
        billInfo.addFloatProperty("money");
        billInfo.addDateProperty("timestamp");
        billInfo.addStringProperty("desc");
        billInfo.addIntProperty("ecard_id");
        billInfo.addIntProperty("user_id");
        billInfo.addFloatProperty("remain");
        billInfo.addStringProperty("user_name");
        billInfo.addIntProperty("year");
        billInfo.addIntProperty("month");
        billInfo.addIntProperty("day");

        Property property = billInfo.addLongProperty("category_id").getProperty();

        Entity billType = schema.addEntity("Category");
        billType.addIdProperty().autoincrement().primaryKey();
        billType.addStringProperty("category_name");

        billInfo.addToOne(billType,property);
    }

    private static void addMessageInfo(Schema schema){
        Entity messageInfo = schema.addEntity("MessageInfo");
        messageInfo.addIdProperty().autoincrement().primaryKey();
        messageInfo.addDateProperty("time");
        messageInfo.addStringProperty("title");
        messageInfo.addStringProperty("desc");
        Property type_id = messageInfo.addLongProperty("type_id").getProperty();
        messageInfo.addLongProperty("sub_id");
        messageInfo.addBooleanProperty("read");
        messageInfo.addStringProperty("json");

        Entity messageType = schema.addEntity("MessageType");
        messageType.addIdProperty().autoincrement().primaryKey();
        messageType.addStringProperty("type_name");

        messageInfo.addToOne(messageType,type_id);
        messageInfo.implementsSerializable();
    }
}
