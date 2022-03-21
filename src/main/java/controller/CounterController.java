package controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 杨文俊
 */
@WebServlet("/Counter")
public class CounterController extends HttpServlet{
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        long amount = Long.parseLong(request.getParameter("amount"));
        double fee = 0;
        double fee1 =0;
        String type = request.getParameter("type");
        if (type.equals("财产案件")) {
            if (amount <= 10000) {
                fee = 50;
            } else if (amount > 10000 && amount <= 100000) {
                fee = 50 + (amount - 10000) * 0.025;
            } else if (amount > 100000 && amount <= 200000) {
                fee = 50 + 2250 + (amount - 100000) * 0.02;
            } else if (amount > 200000 && amount <= 500000) {
                fee = 50 + 2250 + 2000 + (amount - 200000) * 0.015;
            } else if (amount > 500000 && amount <= 1000000) {
                fee = 50 + 2250 + 2000 + 4500 + (amount - 500000) * 0.01;
            } else if (amount > 1000000 && amount <= 2000000) {
                fee = 50 + 2250 + 2000+4500 + 5000 + (amount - 1000000) * 0.009;
            } else if (amount > 2000000 && amount <= 5000000) {
                fee = 50 + 2250 +2000+ 4500 + 5000 + 9000 + (amount - 2000000) * 0.008;
            } else if (amount > 5000000 && amount <= 10000000) {
                fee = 50 + 2250 +2000+ 4500 + 5000 + 9000 + 24000 + (amount - 5000000) * 0.007;
            } else if (amount > 10000000 && amount <= 20000000) {
                fee = 50 + 2250 + 2000 + 4500 + 5000 + 9000 + 24000 + 35000 + (amount - 10000000) * 0.006;
            } else if (amount > 20000000) {
                fee = 50 + 2250 + 2000+ 4500 + 5000 + 9000 + 24000 + 35000 + 60000 + (amount - 20000000) * 0.005;
            }
        }
        else if(type.equals("离婚案件")){
            if (amount<=200000){
                fee=50;
            }
            else {
                fee=50+(amount-200000)*0.005;
            }
            fee1=fee+250;
        }
        else if (type.equals("人格权案件")){
            if (amount<=50000){
                fee=100;
            }
            else if (amount>50000&&amount<=100000){
                fee=100+(amount-50000)*0.01;
            }
            else if (amount>100000){
                fee=100+500+(amount-100000)*0.005;
            }
            fee1=fee+400;
        }
        else if (type.equals("其他非财产案件")){
            fee=50;
            fee1=100;
        }
        NumberFormat ddf1=NumberFormat.getNumberInstance() ;
        ddf1.setMaximumFractionDigits(2);
        String s= ddf1.format(fee);
        String s1= ddf1.format(fee1);
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("fee",s);
        map.put("fee1",s1);
        ObjectMapper mapper = new ObjectMapper();
        //并且传递给客户端
        String json = mapper.writeValueAsString(map);
        mapper.writeValue(response.getWriter(),map);
        System.out.println("11");
    }
}
