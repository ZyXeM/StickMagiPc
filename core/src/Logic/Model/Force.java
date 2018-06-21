package Logic.Model;

public class Force {
    private String name;
    private Vector2D force;
    private boolean permanent;

    public Force(String name, Vector2D force, boolean permanent) {
        this.name = name;
        this.force = force;
        this.permanent = permanent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vector2D getForce() {
        return force;
    }

    public void setForce(Vector2D force) {
        this.force = force;
    }

    public boolean isPermanent() {
        return permanent;
    }

    public void setPermanent(boolean permanent) {
        this.permanent = permanent;
    }
}
