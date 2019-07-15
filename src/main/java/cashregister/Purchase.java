package cashregister;

public class Purchase {

    private Item[] items;

    public Purchase(Item[] items) {

        this.items = items;
    }

    public String asString() {
        String out = "";

        for (Item item : items) {
            out += item.getName() + " " + item.getPrice() + "\n";
        }

        return out;
    }
}
