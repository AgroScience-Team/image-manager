package com.github.agroscienceteam.imagemanager.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Iterator;
import java.util.Map;

public class TestUtils {

  public static void alignIdFields(JsonNode expectedNode, JsonNode actualNode, String key) {
    if (expectedNode.isObject() && actualNode.isObject()) {
      Iterator<Map.Entry<String, JsonNode>> fields = expectedNode.fields();
      while (fields.hasNext()) {
        Map.Entry<String, JsonNode> field = fields.next();
        String fieldName = field.getKey();

        if (key.equals(fieldName)) {
          JsonNode expectedIdValue = field.getValue();
          if (actualNode.has(key)) {
            ((ObjectNode) actualNode).set(key, expectedIdValue);
          }
        } else {
          alignIdFields(field.getValue(), actualNode.get(fieldName), key);
        }
      }
    } else if (expectedNode.isArray()) {
      for (int i = 0; i < expectedNode.size(); i++) {
        alignIdFields(expectedNode.get(i), actualNode.get(i), key);
      }
    }
  }

}