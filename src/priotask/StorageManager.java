package priotask;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {
    private final String filePath = "assignments.json";
    private final Gson gson;

    public StorageManager() {
        this.gson = new GsonBuilder()
                // Explicit JsonSerializer
                .registerTypeAdapter(LocalDate.class, new JsonSerializer<LocalDate>() {
                    @Override
                    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
                        return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
                    } 
                })
                // Explicit JsonDeserializer
                .registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
                    @Override
                    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
                    }
                })
                .setPrettyPrinting()
                .create();
    }

    public void saveTasks(List<Assignment> tasks) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error saving tasks to disk: " + e.getMessage());
        }
    }

    public List<Assignment> loadTasks() {
        File file = new File(filePath);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(filePath)) {
            Type listType = new TypeToken<ArrayList<Assignment>>(){}.getType();
            List<Assignment> tasks = gson.fromJson(reader, listType);
            return tasks != null ? tasks : new ArrayList<>();
        } catch (IOException e) {
            System.out.println("Error loading tasks from disk: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}