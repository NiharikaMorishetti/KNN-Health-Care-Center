import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/AccessoryList")

public class AccessoryList extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Accessory> allAccessoryList = new HashMap<String,Accessory> ();
		try{
		     allAccessoryList = MySqlDataStoreUtilities.getAccessories();
		}
		catch(Exception e)
		{
			
		}		
		String name = null;
		String medicineType = request.getParameter("medicineType");        
		HashMap<String,Accessory> hm = new HashMap<String,Accessory>();
		if(medicineType==null){
			hm.putAll(allAccessoryList);
			name = "";
		}
		else
		{
		   if(medicineType.equals("Accessory"))
		   {
			for(Map.Entry<String,Accessory> entry : allAccessoryList.entrySet()) 
			 {
				if(entry.getValue().getProductType().equals("Accessory"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Accessory";
		   }
		}
		String userType=request.getSession(false).getAttribute("usertype").toString();
		if(userType.equals("Patient") || userType.equals("1"))
		{
			Utilities utility = new Utilities(request,pw);
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			if(name != null && !name.isEmpty())
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+name+" </a></h2></div>");
			}
			else
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'> Accessories </a></h2></div>");
			}
			pw.print("<br>");
			pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
			int i = 1; int size= hm.size();
			for(Map.Entry<String,Accessory> entry : hm.entrySet())
			{
				Accessory accessory = entry.getValue();
				if(i%4==1) pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				pw.print("<h3>"+accessory.getProductName()+"</h3>");
				pw.print("<strong> Price: $"+accessory.getProductPrice()+"</strong>");
				pw.print("<strong> Discount: $"+accessory.getDiscount()+"</strong><ul>");
				pw.print("<strong> Quantity Availiable: "+accessory.getAvailableProductQuantity()+"</strong>");
				if(accessory.getAvailableProductQuantity()==0)
				{
					pw.print("<strong style='color:red;'> Out of Stock!!!</strong>");
				}
				pw.print("<ul><li id='item_2'><img src='images/medicines/"+accessory.getProductImage()+"' alt='' /></li>");
				if(accessory.getAvailableProductQuantity()==0)
				{		
					pw.print("<br><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='"+accessory.getProductType()+"'>"+
					"<input type='hidden' name='medicineName' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='maker' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+accessory.getProductManufacturer()+"'>"+
					"<input type='hidden' name='price' value='"+accessory.getProductPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_disable' value='Buy Now' disabled='disabled'></form></li>");
				}
				else
				{
					pw.print("<br><li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='"+accessory.getProductType()+"'>"+
					"<input type='hidden' name='medicineName' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='maker' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+accessory.getProductManufacturer()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btn_viewpProfile' value='Buy Now'></form></li>");
				}
				pw.print("<br><li><form method='post' action='WriteReviewMedicine'>"+
					"<input type='hidden' name='type' value='"+accessory.getProductType()+"'>"+
					"<input type='hidden' name='maker' value='"+medicineType+"'>"+
					"<input type='hidden' name='medicineName' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+accessory.getProductManufacturer()+"'>"+
					"<input type='hidden' name='price' value='"+accessory.getProductPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btn_review'></form></li>");
				pw.print("<br><li><form method='post' action='ViewReviewMedicine'>"+
					"<input type='hidden' name='type' value='"+accessory.getProductType()+"'>"+
					"<input type='hidden' name='maker' value='"+medicineType+"'>"+
					"<input type='hidden' name='medicineName' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+accessory.getProductManufacturer()+"'>"+
					"<input type='hidden' name='price' value='"+accessory.getProductPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				if(i%4==0 || i == size) pw.print("</tr>");
				i++;
			}	
			pw.print("</table></div></div>");
			utility.printHtml("Footer.html");
		}
		if(userType.equals("Doctor"))
		{
			UtilitiesDoctor utility = new UtilitiesDoctor(request,pw);
			utility.printHtml("HeaderDoctor.html");
			utility.printHtml("LeftNavigationBar.html");
			pw.print("<div id='content_1'>");
			if(name != null && !name.isEmpty())
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+name+" </a></h2></div>");
			}
			else
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'> Accessories </a></h2></div>");
			}
			pw.print("<br>");
			pw.print("<div id='entry_1'><br><table id='bestseller_1'>");
			int i = 1; int size= hm.size();
			for(Map.Entry<String,Accessory> entry : hm.entrySet())
			{
				Accessory accessory = entry.getValue();
				if(i%4==1) pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				pw.print("<h3>"+accessory.getProductName()+"</h3>");
				pw.print("<strong> Price: $"+accessory.getProductPrice()+"</strong>");
				pw.print("<strong> Discount: $"+accessory.getDiscount()+"</strong><ul>");
				pw.print("<strong> Quantity Availiable: "+accessory.getAvailableProductQuantity()+"</strong>");
				if(accessory.getAvailableProductQuantity()==0)
				{
					pw.print("<strong style='color:red;'> Out of Stock!!!</strong>");
				}
				pw.print("<ul><li id='item_2'><img src='images/medicines/"+accessory.getProductImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='post' action='ViewReviewMedicine'>"+
					"<input type='hidden' name='type' value='"+accessory.getProductType()+"'>"+
					"<input type='hidden' name='maker' value='"+medicineType+"'>"+
					"<input type='hidden' name='medicineName' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+accessory.getProductManufacturer()+"'>"+
					"<input type='hidden' name='price' value='"+accessory.getProductPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				if(i%4==0 || i == size) pw.print("</tr>");
				i++;
			}	
			pw.print("</table></div></div>");
			utility.printHtml("Footer.html");
		}
		if(userType.equals("Medicine Manager"))
		{
			UtilitiesMedicineManager utility = new UtilitiesMedicineManager(request,pw);
			utility.printHtml("HeaderMedicineManager.html");
			utility.printHtml("LeftNavigationBarMedicineManager.html");
			pw.print("<div id='content_1' style='margin-top:-740px;'>");
			if(name != null && !name.isEmpty())
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'>"+name+" </a></h2></div>");
			}
			else
			{
				pw.print("<h2><a style='margin-left:15px;font-weight: normal;font-family:Century Schoolbook;color:white;font-size:34px;text-decoration:none;'> Accessories </a></h2></div>");
			}
			pw.print("<br>");
			pw.print("<div id='entry_1' style='margin-top:-678px;'><br><table id='bestseller_1'>");
			int i = 1; int size= hm.size();
			for(Map.Entry<String,Accessory> entry : hm.entrySet())
			{
				Accessory accessory = entry.getValue();
				if(i%4==1) pw.print("<tr>");
				pw.print("<td><div id='shop_item_2'>");
				pw.print("<h3>"+accessory.getProductName()+"</h3>");
				pw.print("<strong> Price: $"+accessory.getProductPrice()+"</strong>");
				pw.print("<strong> Discount: $"+accessory.getDiscount()+"</strong><ul>");
				pw.print("<strong> Quantity Availiable: "+accessory.getAvailableProductQuantity()+"</strong>");
				if(accessory.getAvailableProductQuantity()==0)
				{
					pw.print("<strong style='color:red;'> Out of Stock!!!</strong>");
				}
				pw.print("<ul><li id='item_2'><img src='images/medicines/"+accessory.getProductImage()+"' alt='' /></li>");
				pw.print("<br><li><form method='post' action='ViewReviewMedicine'>"+
					"<input type='hidden' name='type' value='"+accessory.getProductType()+"'>"+
					"<input type='hidden' name='maker' value='"+medicineType+"'>"+
					"<input type='hidden' name='medicineName' value='"+accessory.getProductName()+"'>"+
					"<input type='hidden' name='manufacturer' value='"+accessory.getProductManufacturer()+"'>"+
					"<input type='hidden' name='price' value='"+accessory.getProductPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btn_review'></form></li>");
				pw.print("</ul></div></td>");
				if(i%4==0 || i == size) pw.print("</tr>");
				i++;
			}	
			pw.print("</table></div></div>");
			utility.printHtml("Footer.html");
		}
	}	
}
