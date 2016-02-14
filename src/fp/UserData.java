package fp;

public class UserData implements java.io.Serializable {
    protected int userType;
    protected String username;
    protected String password;
    protected int money;
    protected int total_fish_number;
    protected int kind1_fish_number;
    protected int kind2_fish_number;

    private int fish_number_Max = 30;

    protected int [][] fish_data = new int[fish_number_Max][2];

    protected int user_index;
    
    public void setUserType(int type) { userType = type; }
    public int getUserType() { return userType; }
    public void setUserName(String name) { username = name; }
    public String getUserName() { return username; }
    public void setUserPassword(String passwd) { password = passwd; }
    public String getUserPassword() { return password; }
    public void setMoney(int input_money) { money = input_money; }
    public int getMoney() { return money; }
    public void setTotalFish(int number) { total_fish_number = number; }
    public int getTotalFish() { return total_fish_number; }
    public void setKind1FishNumber(int number) { kind1_fish_number = number; }
    public int getKind1FishNumber() { return kind1_fish_number; }
    public void setKind2FishNumber(int number) { kind2_fish_number = number; }
    public int getKind2FishNumber() { return kind2_fish_number; }

    public UserData() {}
}