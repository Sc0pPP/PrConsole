public abstract class Person {
    private int id;
    private String name;

    public Person(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract String getRole();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Person)) {
            return false;
        }
        Person other = (Person) o;
        return id == other.id;
    }

    @Override
    public String toString() {
        return getRole() + " №" + id + ": " + name;
    }
}
