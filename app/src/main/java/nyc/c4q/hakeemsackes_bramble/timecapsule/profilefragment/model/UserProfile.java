package nyc.c4q.hakeemsackes_bramble.timecapsule.profilefragment.model;

import java.util.List;

/**
 * Created by catwong on 3/7/17.
 */

public class UserProfile {

    List<User> users;

    public UserProfile(){}

    public UserProfile(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
