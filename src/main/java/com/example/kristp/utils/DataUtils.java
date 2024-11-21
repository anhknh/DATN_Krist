package com.example.kristp.utils;


import com.example.kristp.entity.KhuyenMai;
import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.util.Locale;

import java.util.Locale;
@Service
public class DataUtils {
  public static String formatCurrency(double amount){
      NumberFormat currrencyFormat = NumberFormat.getNumberInstance(new Locale("vi","VN"));
      return currrencyFormat.format(amount);
  }

  public  static  String calculatorTotal(double tongTien, KhuyenMai khuyenMai){
      if(khuyenMai != null){
          if(khuyenMai.getKieuKhuyenMai().equals("VND")){
              double check = tongTien - khuyenMai.getGiaTri() + 30000;
              if (check < 0){
                  return  formatCurrency(0);
              }
              return formatCurrency(check);
          }
      }
      return formatCurrency(tongTien + 30000);
  }
    public  static  double calculatorTotal2(double tongTien, KhuyenMai khuyenMai){
        if(khuyenMai != null){
            if(khuyenMai.getKieuKhuyenMai().equals("VND")){
                double check = tongTien - khuyenMai.getGiaTri() + 30000;
                if (check < 0){
                    return  0;
                }
                return check;
            }
        }
        return (tongTien + 30000);
    }
}
