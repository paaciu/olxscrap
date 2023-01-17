package paaciu.webscrapper;


import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.List;
import java.io.FileWriter;
import java.util.Scanner;

public class WebScrapperMain {

    public static void main(String[] args) {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setPrintContentOnFailingStatusCode(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getCookieManager().setCookiesEnabled(true);
        Scanner sc = new Scanner(System.in);
        System.out.println("What brand are You looking for?: (if any leave empty)");
        String carBrand = sc.nextLine();
        System.out.println("What is maximum price?: (if any leave empty)");
        String carPrice = sc.nextLine();
        System.out.println("Should it be newer than?: (year ex. 2005)");
        String productionYear = sc.nextLine();
        System.out.println("Damaged?: (true or false)");
        Boolean carCondition = sc.nextBoolean();
        System.out.println("How many pages you want to scrap?:");
        int pagesAmount = sc.nextInt();
        String olxPageBuild = LinkBuilder.olxBuild(carBrand, carPrice, carCondition, productionYear);
//        String otoPageBuild = LinkBuilder.otoBuild(carBrand,carPrice, productionYear);
        String nextPage = "&page=";

        try {
            FileWriter olxOffer = new FileWriter("olxcars.csv", true);
            for (int i = 1; i <= pagesAmount; i++) {
                String olxURL = olxPageBuild + nextPage + i;
                HtmlPage olxPage = webClient.getPage(olxURL);
                webClient.getCurrentWindow().getJobManager().removeAllJobs();
                List<?> olxAnchors = olxPage.getByXPath("//a[@class='css-rc5s2u']");
                for (int j = 0; j < olxAnchors.size(); j++) {
                    HtmlAnchor link = (HtmlAnchor) olxAnchors.get(j);
                    String olxCarLink = link.getHrefAttribute();
                    if (olxCarLink.startsWith("https://www.otomoto.pl/")) {
                        olxCarLink = "";
                    } else if (olxCarLink.startsWith("/d")){
                        olxCarLink = "https://olx.pl/oferta" + olxCarLink;
                    }
                    if (olxCarLink.length()>1) {
                        olxOffer.write(olxCarLink + "\n");
                    }
                }
                i++;
            }
            olxOffer.close();
        }catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
//        try {
//            FileWriter otoOffers = new FileWriter("otocars.csv", true);
//            for (int k = 1; k <= pagesAmount; k++) {
//                String otoURL = otoPageBuild + nextPage + k;
//                URL address = new URL(otoURL);
//                WebRequest wr = new WebRequest(address)
//                        .setAdditionalHeader();
//                wr.setAdditionalHeader("from htmlunit", "yes");
//                HtmlPage otoPage = webClient.getPage(wr);
//                List<?> otoAnchors = otoPage.getByXPath("//*[@id=\"__next\"]/div/div/div/div[1]/div[2]/div[2]/div[1]/div[3]/main");
//                for (int j = 0; j < otoAnchors.size(); j++) {
//                    HtmlAnchor otoLink = (HtmlAnchor) otoAnchors.get(j);
//                    String otoCarLink = otoLink.getHrefAttribute();
//                    otoOffers.write(otoCarLink + "\n");
//                }
//                k++;
//            }
//            otoOffers.close();
//        }catch (IOException e) {
//            System.out.println("An error occurred: " + e);
//        }
        webClient.close();

    }
}
