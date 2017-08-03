package com.javarush.task.task21.task2107;

import javax.jws.soap.SOAPBinding;
import java.util.LinkedHashMap;
import java.util.Map;

/* 
Глубокое клонирование карты
*/
public class Solution implements Cloneable{


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Solution)) return false;

        Solution solution = (Solution) o;

        return users.equals(solution.users);
    }

    @Override
    public int hashCode() {
        return users.hashCode();
    }

    public Solution clone() throws CloneNotSupportedException {
        Solution clone = new Solution();
        for(Map.Entry e : this.users.entrySet())
        {
            User user = (User) e.getValue();
            clone.users.put((String) e.getKey(), user.clone());
        }
        return clone;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.users.put("Hubert", new User(172, "Hubert"));
        solution.users.put("Zapp", new User(41, "Zapp"));
        Solution clone = null;
        try {
            clone = solution.clone();
            System.out.println(solution);
            System.out.println(clone);

            System.out.println(solution.users);
            System.out.println(clone.users);

        } catch (CloneNotSupportedException e) {
            e.printStackTrace(System.err);
        }


    }

    protected Map<String, User> users = new LinkedHashMap();

    public static class User implements Cloneable {
        int age;
        String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof User)) return false;

            User user = (User) o;

            if (age != user.age) return false;
            return name.equals(user.name);
        }

        @Override
        public int hashCode() {
            int result = age;
            result = 31 * result + name.hashCode();
            return result;
        }

        public User clone()
        {
            return new User(this.age, this.name);
        }

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }
    }
}
