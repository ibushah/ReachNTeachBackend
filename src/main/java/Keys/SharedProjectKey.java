//package Keys;
//
//import com.example.hubProject.Model.Projects;
//import com.example.hubProject.Model.User;
//import com.fasterxml.jackson.annotation.JsonBackReference;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Objects;
//
//
//public class SharedProjectKey implements Serializable {
//
//
//
//    private Long projectId;
//
//
//
//    private String user;
//
//    public SharedProjectKey() {
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        SharedProjectKey that = (SharedProjectKey) o;
//        return Objects.equals(projectId, that.projectId) &&
//                Objects.equals(user, that.user);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(projectId, user);
//    }
//}