import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.HashMap;
import java.util.Objects;

@WebServlet("/GetUserNumbers")
public class GetUserNumbers extends HttpServlet {
    private Cipher cipher;
    private KeyPair pair;



        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            HttpSession session = request.getSession();

            //If user clicks to check if he won variable wont be null
            String check_lottery = request.getParameter("check_lottery");

            //Retrieve and slice password to fit txt file name
            String pass = (String) session.getAttribute("pass");
            String passSliced = pass.substring(0,19);

            BufferedReader reader;

            //Check if the txt file exists
            File tempFile = new File("/tmp/"+passSliced);
            if (tempFile.exists()){
                try{
                    //Start a reader to retrieve from txt file
                    reader = new BufferedReader(new FileReader("/tmp/"+passSliced));
                    String line = reader.readLine();

                    //Build table with numbers
                    StringBuilder content = new StringBuilder("<div class='table-responsive text-center shadow-sm'>" +
                            "<table class='table table-striped table-hover'>" +
                            "<thead>" +
                            "<tr><th>Lottery Numbers</th><</tr>"+
                            "</thead>" +
                            "<tbody>");

                    //Store number key pairs, to later check if match with lottery number
                    HashMap<String, String> lott_numbers = new HashMap<String, String>();


                    while (line != null) {
                        //Turn byte into hexadecimal format
                        byte[] passHexed = offUser.makeHex(line);

                        //Decrypt number using session private key
                        String lotNum = decryptData(passHexed, (Key) session.getAttribute("private_key"));

                        //Add number to hash table
                        lott_numbers.put(lotNum,lotNum);

                        //Add number to html table
                        content.append("<tr><td>").append(lotNum).append("</td></tr>");

                        //Proceed to next line
                        line = reader.readLine();
                    }

                    reader.close();
                    content.append("</tbody>");
                    content.append("</table>");
                    content.append("</div>");

            //If check lottery button is clicked enter here
            if (check_lottery != null && check_lottery.equals("check_lottery")) {

                // Delete all files as draw occurs and numbers are stored in hash
                File dir = new File("/tmp");
                if(dir.exists()) {
                    for (File file : dir.listFiles()){
                        if (!file.isDirectory()){
                            file.delete();
                            System.out.println(file);}
                }}



                //Winning number added to the session from database during login
                String winningNum = (String) session.getAttribute("winning_num");

                StringBuilder resp;

                //Respond with Friends GIF if successful
                if (lott_numbers.containsKey(winningNum)){
                    resp = new StringBuilder("<img src=\"https://media1.tenor.com/images/10dfebf17a6365ad7f3fb90536a63de7/tenor.gif?itemid=12767173\" alt=\"You Won!!!\"  width=500/>");
                }else {
                    //Respond with Alert of failure.
                    resp = new StringBuilder("<div role=\"alert\" class=\"alert alert-danger\"><span><strong>Better luck next time!!!</strong>   (deleting all draws)</span></div>");
                }
                request.setAttribute("lot_table", resp);
            }
            else {
            request.setAttribute("lot_table", content);
            }
            request.getRequestDispatcher("/account.jsp").forward(request, response);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
                //In the Case no number inserted
            }else {
                StringBuilder resp = new StringBuilder( "<div role=\"alert\" class=\"alert alert-danger\"><span><strong>Please Insert Numbers First!</strong></span></div>");
                request.setAttribute("lot_table", resp);
                request.getRequestDispatcher("/account.jsp").forward(request, response);}
        }


    public String decryptData (byte[] data, Key privateKey) {
        try {
            //Initialize cipher
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");

            //Insert private key from session
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            //Decrypt txt line
            byte[] decipheredText = cipher.doFinal(data);

            return new String(decipheredText, StandardCharsets.UTF_8);
        } catch (InvalidKeyException | BadPaddingException | IllegalBlockSizeException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }



}





