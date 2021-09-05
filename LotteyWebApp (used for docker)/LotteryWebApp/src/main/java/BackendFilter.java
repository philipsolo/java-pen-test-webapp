import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Map;

@WebFilter(filterName = "BackendFilter")
public class BackendFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        boolean invalid = false;

        //Retrieve all input
        Map params = request.getParameterMap();


        if(params != null){

            //Iterate over each element
            for (Object o : params.keySet()) {
                String key = (String) o;
                String[] values = (String[]) params.get(key);

                //Iterate over each value
                for (String value : values) {
                    //Check If key word present
                    if (checkChars(value)) {
                        invalid = true;
                        break;
                    }
                }
                if (invalid) {
                    break;
                }
            }
        }
            //If invalid return error page
             if(invalid){
                 try {
                     request.setAttribute("message", "Illegal Character Usage (eg. 'Script', '>')");
                     request.getRequestDispatcher("/error.jsp").forward(request,resp);
                 }
                 catch (Exception e){e.printStackTrace();}
             }

             else {
                 try {
                     chain.doFilter(request,resp);
                 }
                 catch (Exception e){
                     e.printStackTrace();
                     request.setAttribute("message", "Illegal Character Usage (eg. 'Script', '>')");
                     request.getRequestDispatcher("/error.jsp").forward(request, resp);
                 }


             }
    }

    public static boolean checkChars(String value) {
        //Return invalid if found method
        boolean invalid = false;
        String[] badChars = { "<", ">","!", "{", "}","insert","into","where", "script", "delete", "input" };

        for (String badChar : badChars) {
            if (value.contains(badChar)) {
                invalid = true;
                break;
            }
        }
        return invalid;
    }
    public void init(FilterConfig config) throws ServletException {
        config.getServletContext().log("The Filter has BEGUN");
    }

}
