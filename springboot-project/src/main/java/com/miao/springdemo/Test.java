package com.miao.springdemo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        List<String> cityList = new ArrayList<>();
        // 填写所有城市名称到cityList中
        cityList.add("北京");
        cityList.add("河北");
        cityList.add("山西");
        cityList.add("辽宁");
        cityList.add("吉林");
        cityList.add("黑龙江");
        cityList.add("江苏");
        cityList.add("浙江");
        cityList.add("安徽");
        cityList.add("福建");
        cityList.add("江西");
        cityList.add("山东");
        cityList.add("河南");
        cityList.add("湖北");
        cityList.add("湖南");
        cityList.add("广东");
        cityList.add("海南");
        cityList.add("四川");
        cityList.add("贵州");
        cityList.add("云南");
        cityList.add("陕西");
        cityList.add("甘肃");
        cityList.add("青海");
        cityList.add("台湾");
        cityList.add("内蒙古");
        cityList.add("广西");
        cityList.add("西藏");
        cityList.add("宁夏");
        cityList.add("新疆");
        cityList.add("天津");
        cityList.add("上海");
        cityList.add("重庆");
        cityList.add("香港");
        cityList.add("澳门");











        List<String[]> data = new ArrayList<>();

        // 生成数据
        Random random = new Random();
        int startYear = 2015;
        int endYear = 2015;

        for (int year = startYear; year <= endYear; year++) {
            for (String city : cityList) {
                int minMedicalVisits = 50000 + (year - startYear) * 10000;  // 根据年份逐渐增加最小就医数量
                int maxMedicalVisits = minMedicalVisits + 15000;  // 最大就医数量为最小数量加上一个范围
                int medicalVisits = random.nextInt(maxMedicalVisits - minMedicalVisits + 1) + minMedicalVisits;  // 随机生成就医数量
                int hospitals = random.nextInt(50) + 16;  // 随机生成医院数量（范围：16-65）
                String[] record = {city, String.valueOf(year), String.valueOf(medicalVisits), String.valueOf(hospitals)};
                data.add(record);
            }
        }

        // 写入CSV文件
        String filePath = "medical_data.csv";  // 输出文件路径（相对项目根目录）
        try (FileWriter writer = new FileWriter(filePath)) {
            // 写入表头
            writer.write("城市,年份,就医数量,医院数量\n");
            // 写入数据
            for (String[] record : data) {
                writer.write(String.join(",", record) + "\n");
            }
            System.out.println("数据生成成功，已保存到：" + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
