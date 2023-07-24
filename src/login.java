import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class login extends JDialog {
    private JPanel pannel;
    private JLabel title;
    private JTextField tfemail;
    private JPasswordField pfpassword;
    private JButton logMeInButton;
    private JButton cancelButton;
    private JButton registerButton;
    private JLabel tfemail1;
    private JLabel tfpassword1;

    public login(JFrame parent) {
        super(parent);
        setTitle("create a new account");
        setContentPane(pannel);
        setMinimumSize(new Dimension(450, 474));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        logMeInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginUser();
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }

    private void loginUser() {
        String email = tfemail.getText();
        String password = String.valueOf(pfpassword.getPassword());
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "please enter all fields", "try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        u1 = checkUserDatabase(email, password);
        if (u1 != null) {
            dispose();
        } else {
           

        }
    }



    private void registerUser() {
        String email=tfemail.getText();
        String password = String.valueOf(pfpassword.getPassword());
        if( email.isEmpty()||password.isEmpty()){
            JOptionPane.showMessageDialog(this,"please enter all fields","try again",JOptionPane.ERROR_MESSAGE);
            return;
        }
        u1 =addUsertoDatabase(email,password);
        if(u1 !=null){
            dispose();
        }
        else{
            JOptionPane.showMessageDialog(this,"failed to register","Try again!",JOptionPane.ERROR_MESSAGE);

        }
    }
    public user u1;

    private user addUsertoDatabase(String email,String password) {
        user u1 = null;
        final String DB_URL ="jdbc:mysql://localhost:3306/db";
        final String USERNAME= "root";
        final String PASSWORD = "2002";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement smt = conn.createStatement();
            String query = "Insert into userslogin  (email,password)"+"VALUES(?,?);";
            PreparedStatement preparedStatement= conn.prepareStatement(query);
            preparedStatement.setString(1,email);

            preparedStatement.setString(2,password);
            int addedRows = preparedStatement.executeUpdate();
            if(addedRows>0){
                u1 = new user();
                u1.email= email;
                u1.password=password;
            }
            smt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error :"+e);
        }
        return u1;

        }
    private user checkUserDatabase(String email,String password) {
        String s1 = new String();
        user u1 = null;
        final String DB_URL ="jdbc:mysql://localhost:3306/db";
//            jdbc:mysql://localhost:3306/mysql
        final String USERNAME= "root";
        final String PASSWORD = "2002";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            String query = "Select * from userslogin where email ='"+tfemail.getText()+"';";
            Statement smt = conn.createStatement();
            ResultSet rs = smt.executeQuery(query);


            while(rs.next()) {
                s1 = rs.getString(1);
                System.out.println("password =" + s1.toLowerCase());
                System.out.println("pwd: " + pfpassword.getText());
                System.out.println(tfemail.getText());
            }



            if(pfpassword.getText().equals(s1)){
                System.out.println("logged in");
                JOptionPane.showMessageDialog(this,"Logged in ","Good luck!",JOptionPane.INFORMATION_MESSAGE);



            }
            else{
                JOptionPane.showMessageDialog(this,"failed to login ","Try again!",JOptionPane.ERROR_MESSAGE);

            }

            smt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error :"+e);
        }
        return u1;

    }
        public static void main(String[] args) {
        login myForm = new login(null);
        user u1 = myForm.u1;
        if(u1!=null){
            System.out.println("Successfully registered of name : "+u1.email);
        }else {
            System.out.println("registration Cancelled");
        }

    }
}

