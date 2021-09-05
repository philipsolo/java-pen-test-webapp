import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.*;



@WebServlet("/AddUserNumbers")
public class AddUserNumbers extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;


        // Retrieve lottery numbers and normalized them with comma for insertion to text file
        String finalSt = request.getParameter("num1") + "," + request.getParameter("num2") + "," +
                request.getParameter("num3") + "," + request.getParameter("num4") + "," +
                request.getParameter("num5") + "," + request.getParameter("num6");


        HttpSession session = request.getSession();

        //Retrieve the password from the session
        String pass = (String) session.getAttribute("pass");

        //Slice password for the name of the txt file
        String passSliced = pass.substring(0, 19);

        //Create file and encryption pair for user if not existent
        File f = new File("/tmp/"+passSliced);
        if (f.exists() && !f.isDirectory()) {
            System.out.println("Already set");
        }
        else {
            System.out.println("Creating key pair");
            try{
                if ((Key) session.getAttribute("private_key") == null){

                    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                    KeyPair pair = keyPairGen.generateKeyPair();

                    session.setAttribute("private_key", pair.getPrivate());
                    session.setAttribute("public_key", pair.getPublic());}
                else {
                    System.out.println("Key pair already set");
                }
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }

        //Function to encrypt the data
        byte[] dataEncrypted = encryptData(finalSt, (Key) session.getAttribute("public_key"));

        //Insert encrypted data into file
        try {
            fw = new FileWriter("/tmp/"+passSliced, true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.println(offUser.transHex(dataEncrypted));
            pw.flush();

            fw.close();
            bw.close();
            pw.close();

        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("infoResponse", "Error Inserting Numbers" + e);
            request.getRequestDispatcher("/account.jsp").forward(request, response);

        }
        //Alert User of the inserted numbers
        StringBuilder alert = new StringBuilder("<div role=\"alert\" class=\"alert alert-primary\"><span><strong>"+finalSt+"</strong> Added</span></div>");

        request.setAttribute("infoResponse", alert);
        request.getRequestDispatcher("/account.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);

    }

    public byte[] encryptData(String data, Key publicKey) {
        try{
            //Create cipher
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            //Initialize cipher using session public key created previously
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipher.update(data.getBytes());
            return cipher.doFinal();
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex){
            ex.printStackTrace();
        }
        return null;
    }

}



