package com.genuinecoder.springserver;

import com.genuinecoder.springserver.service.FoodDao;

import java.sql.SQLException;

import javax.sql.rowset.serial.SerialException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class SpringServerApplicationTests {

  @Autowired
  private FoodDao foodDao;

  @BeforeAll
  public void clear() {
//    List<Food> employees = foodDao.getAllFoods();
//    for (Food employee : employees) {
//     foodDao.delete(employee);
//    }
  }

  @Test
  private void addEmployee(String name, String location, String branch) throws SerialException, SQLException {
//    Food food = new Food();
//   
//    food.setName("Com ga xa xiu");
//	//byte[] imageBytes = null;
//	// Take photo
//	System.out.print("Test");
//	String filePath = "D:\\foodapp\\serverapp\\src\\main\\resources\\static\\Mon1.jpg";
//	try {
//        File file = new File(filePath);
//        byte[] bytesArray = new byte[(int) file.length()];
//
//        FileInputStream fis = new FileInputStream(file);
//        fis.read(bytesArray); // Đọc dữ liệu từ file vào mảng bytesArray
//        fis.close();
//        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytesArray);
//        // In ra độ dài của mảng bytes để kiểm tra
//        System.out.println("Length of bytes array: " + bytesArray.length);
//        String base64String = Base64.getEncoder().encodeToString(bytesArray);
//        food.setBase64(base64String);
//        food.setImage(blob);
//        // Bây giờ bạn có thể sử dụng mảng bytesArray cho mục đích của mình
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
//	food.setGuide("Nau mon nay de lam");
//	food.setIngredient("1 con ga 1 chen com");
//    foodDao.save(food);
  }

}
