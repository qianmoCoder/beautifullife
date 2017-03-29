package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.ddu.bean");
        addStudyContent(schema);
        new DaoGenerator().generateAll(schema, "../app/src/main/java-gen");
    }

    private static void addStudyContent(Schema schema){
        Entity entity = schema.addEntity("StudyContent");
        entity.addIdProperty();
        entity.addStringProperty("title");
        entity.addStringProperty("description");
        entity.addStringProperty("type");
    }

}
