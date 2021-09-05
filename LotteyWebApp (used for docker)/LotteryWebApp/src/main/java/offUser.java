import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class offUser{

    private String username;
    private String password;
    private String salt;

    public offUser(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }

    //Setters and getters for user info
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt(){ return salt; }

    public void setSalt(String salt) {
        this.salt = salt;
    }


    //Check if user inserted password matches database password
    protected boolean userValidate(String dbPassword, String salt, String userPassword){
        String passHashed = passHash(userPassword, makeHex(salt));
        return dbPassword.equals(passHashed);
    }


    String passHash(String password, byte[] salt) {
        // Generate salt if not provided (when user first creates account)
        if (salt == null){
        try {
            salt = makeSalt();
        }catch (NoSuchAlgorithmException e){
            System.out.println("Salty Error");
        }}

        byte[] passHashed = null;

        //Hash the password and add the salt
        if (salt != null){
            try {
                passHashed = saltedHash(password,salt);
            }
            catch (NoSuchAlgorithmException e){
                System.out.println("Hashing Error");
            }
        }

        //Turn byte array into Hexadecimal
        this.salt = transHex(salt);
        this.password = transHex(passHashed);

        return this.password;
    }


    //Hash password with salt
    public static byte[] saltedHash(String password, byte[] salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        byte[] bitten = md.digest(password.getBytes());
        md.reset();
        return bitten;
    }

    //Create random salt
    public static byte[] makeSalt() throws NoSuchAlgorithmException{
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");

        //Initialize 16 byte array for salt
        byte[] salt = new byte[16];

        //insert salt into byte array
        secureRandom.nextBytes(salt);
        return salt;
    }


    //Turn any string into a hexadecimal byte[] array
    public static byte[] makeHex(String hex){
        byte[] binary = new byte[hex.length()/2];
        for (int i = 0; i<binary.length; i++){
            binary[i] = (byte) Integer.parseInt(hex.substring(2*i,2*i+2),16);
        }
        return binary;
    }

    //Transcode any hexadecimal byte array into a String
    public static String transHex(byte[] arr){
        BigInteger bigInt = new BigInteger(1, arr);
        String hex = bigInt.toString(16);
        int padLen = (arr.length *2) - hex.length();
        if (padLen > 0){
            return String.format("%0" + padLen +"d",0) + hex;
        }
        else {
            return hex;
        }
    }
}
