package paaciu.webscrapper;

public class LinkBuilder {
    public String brand;
    public String price;
    public boolean isDamaged;
    public String year;

    public static String olxbasic = "https://www.olx.pl/d/motoryzacja/samochody/";
    public static String otobasic = "https://www.otomoto.pl/osobowe/";

    public void OlxLinkBuild(String brand, String price, boolean isDamaged, String year) {
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.isDamaged = isDamaged;
    }

    public void OtomotoLinkBuild(String brand, String price, boolean isDamaged, String year) {
        this.brand = brand;
        this.year = year;
        this.price = price;
        this.isDamaged = isDamaged;
    }

     public static String olxBuild(String brand, String price, boolean isDamaged, String year) {
        String olxLink = "";
        if (!brand.isEmpty()) {
            olxLink = olxbasic + brand.toLowerCase() + "/?";
        } else olxLink = olxbasic + "?";
        if (!price.isEmpty()) {
            olxLink += "search%5Bfilter_float_price:to%5D=" + price + "&";
        }
        if(!year.isEmpty()){
            olxLink += "search%5Bfilter_float_year:from%5D=" + year + "&";
        }
        if (!isDamaged) {
            olxLink += "search%5Bfilter_enum_condition%5D%5B0%5D=notdamaged" + "&";
        } else olxLink +="search%5Bfilter_enum_condition%5D%5B0%5D=damaged" + "&";

        return olxLink;
    }

    public static String otoBuild(String brand, String price, String year) {
        String otoLink = "";
        if (!brand.isEmpty()) {
            otoLink = otobasic + brand.toLowerCase();
        } else otoLink = otobasic;
        if (!year.isEmpty()){
            otoLink += "/" + "od-" + year + "?";
        } else otoLink += "?";
        if (!price.isEmpty()){
            otoLink += "search%5Bfilter_float_price%3Ato%5D=" + price;
        }
//        if (!isDamaged){
//            otoLink += "search%5Bfilter_enum_damaged%5D=1&search%5Badvanced_search_expanded%5D=false";
//        } else otoLink += "search%5Bfilter_enum_damaged%5D=1&search%5Badvanced_search_expanded%5D=true";


        return otoLink;
    }
}

