package attributes;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class launch {

	public static void main(String[] args) throws ParseException, IllegalAccessException, InstantiationException,
			SQLException, JsonSyntaxException, JsonIOException, UndeclaredThrowableException, IOException {

		
		/*String s="{\r\n" + 
				"  \"category_name\" : \"mens_footwear\",\r\n" + 
				"  \"categoryPath\" : \"Footwear>Men>Slippers & Flip Flops\",\r\n" + 
				"  \"name\" : \"Sparx SFG-2006 Slippers\",\r\n" + 
				"  \"description\" : \"\",\r\n" + 
				"  \"regular_price\" : 749,\r\n" + 
				"  \"sale_price\" : 674,\r\n" + 
				"  \"stock\" : false,\r\n" + 
				"  \"image\" : \"https://rukminim1.flixcart.com/image/800/800/slipper-flip-flop/4/r/z/black-red-sfg-2006-sparx-10-original-imadnv3nzaeayuyx.jpeg?q=90\",\r\n" + 
				"  \"product_url\" : \"https://www.flipkart.com/sparx-sfg-2006-slippers/p/itmfafyd4f9zxnxh?pid=SFFDN8UDMGZHQHHX&affid=instantweb\",\r\n" + 
				"  \"product_id\" : \"1_SFFDN8UDMGZHQHHX\",\r\n" + 
				"  \"highlights\" : [ \"Red, Black Color\", \"Type: Slippers\", \"For Men\" ],\r\n" + 
				"  \"active_id\" : \"SFFDN8UDMGZHQHHX\"\r\n" + 
				"}\r\n" + 
				"";
		String r=transform.doCatStore(s);
		System.out.println(r);*/
		
		/*String d = "call productInsUpdate(\"Girls\", \"Girls\",\"Combo Sets\",\"Gkidz Girls Casual T-shirt\",\" Primary Product Type: T-shirt,Character: No Character\",\"1944.0\",\"999.0\",\"false\",\"https://rukminim1.flixcart.com/image/800/800/night-suit/m/9/h/n-jg-3pck-style-4-col-jg3pckshrtcmb-3-gkidz-5-6-years-original-imae7jsvhtjmyvjn.jpeg?q=90\",\"https://www.flipkart.com/gkidz-girls-casual-t-shirt/p/itmf3vrfhkxhdjus?pid=ACBE6WQNGEV33Y63&affid=instantweb\",\"ACBE6WQNGEV33Y63\",\"1\",\"0\",\"null\",\"ACBE6WQN24JRX3HR,ACBE6WQN2MRZYT4M,ACBE6WQN2PQVDZZ7,ACBE6WQN36H4GZR9,ACBE6WQN3CFSZPG7,ACBE6WQN3KY83WCS,ACBE6WQN3RKB2GFP,ACBE6WQN3RKRWDBC,ACBE6WQN3RQ2TRKQ,ACBE6WQN3SGSHVPW,ACBE6WQN3SUGS5FB,ACBE6WQN3ZHD78KY,ACBE6WQN45RXNZ8N,ACBE6WQN49WEXPGG,ACBE6WQN4RMVH7FG,ACBE6WQN4YQRECED,ACBE6WQN527HDMWY,ACBE6WQN57Y7V4KR,ACBE6WQN5GWYKQ5V,ACBE6WQN5HRH2EHK,ACBE6WQN5HWXQQEB,ACBE6WQN5S29BGWS,ACBE6WQN5SQCTJNH,ACBE6WQN5TQHCV3U,ACBE6WQN64GZXRXM,ACBE6WQN6SHPSRFH,ACBE6WQN6YMDJFQU,ACBE6WQN747T4XV9,ACBE6WQN74GGPYRS,ACBE6WQN87ZUVJ4U,ACBE6WQN89WNK6UK,ACBE6WQN8C9VTC22,ACBE6WQN8FAWX9HG,ACBE6WQN8QAHGVZ9,ACBE6WQN9CYKGRZE,ACBE6WQN9H2KGCCG,ACBE6WQN9HYWFFSJ,ACBE6WQNA4WCHZCU,ACBE6WQNA5VBAV43,ACBE6WQNAGQGCK2F,ACBE6WQNANHTHGXJ,ACBE6WQNAPRBKFXW,ACBE6WQNB373JGTH,ACBE6WQNB7QQAMG5,ACBE6WQNBDDYTKBR,ACBE6WQNBPQZ9F2M,ACBE6WQNBSNFUZTM,ACBE6WQNBV4FSX4P,ACBE6WQNBWNQBGKT,ACBE6WQNBXAPUGYM,ACBE6WQNBZRHWM9D,ACBE6WQNC3Q8EHG4,ACBE6WQNC6WWHPBY,ACBE6WQNC6XAHNYZ,ACBE6WQNCAEEXYXC,ACBE6WQNCBESXZES,ACBE6WQNCE57XFSH,ACBE6WQNCFCRW8KP,ACBE6WQNCGR4X8JH,ACBE6WQNCH2RDYQJ,ACBE6WQNCHEHPZFP,ACBE6WQNCQSVWEGD,ACBE6WQNCRXMHT3K,ACBE6WQNCU4KGNBZ,ACBE6WQNCVSVYVFM,ACBE6WQNCWBADCUM,ACBE6WQNCWXQSFET,ACBE6WQNCXZHYYKA,ACBE6WQNCYFQNQHG,ACBE6WQNCYM48DGN,ACBE6WQND9MXQZGZ,ACBE6WQNDAFWUGTB,ACBE6WQNDCCFQBDU,ACBE6WQNDHDGFTBF,ACBE6WQNDMGMEZZE,ACBE6WQNDNEUFKZF,ACBE6WQNDNYVZMJG,ACBE6WQNDVGJPNBX,ACBE6WQNDVJQ6GYM,ACBE6WQNDYCY6SMB,ACBE6WQNEDB2BWTZ,ACBE6WQNEGHYYGWA,ACBE6WQNEHAAPWVT,ACBE6WQNEHF3FYYD,ACBE6WQNEHYRZ4QW,ACBE6WQNEQ8TGMUA,ACBE6WQNESEZZ5DG,ACBE6WQNEVN4HQ3Q,ACBE6WQNEVZ4FY6J,ACBE6WQNEWYYEZSE,ACBE6WQNEZGGYGQK,ACBE6WQNFABQGQZZ,ACBE6WQNFB7GMNUR,ACBE6WQNFBHYV7UF,ACBE6WQNFJVKCRKD,ACBE6WQNFKRHSV8H,ACBE6WQNFMD93PFD,ACBE6WQNFNFSJNJG,ACBE6WQNFNRBTNPX,ACBE6WQNFSZ6HFCK,ACBE6WQNFTYHXWRN,ACBE6WQNFVGUVE48,ACBE6WQNFXVY4YQZ,ACBE6WQNGCJB3W2Q,ACBE6WQNGD8N5ZHQ,ACBE6WQNGF4ZTNFZ,ACBE6WQNGFBRQ8BW,ACBE6WQNGFXK7ZEE,ACBE6WQNGHAWDC43,ACBE6WQNGPPZTNCX,ACBE6WQNGS95VHXZ,ACBE6WQNGVMRYTPB,ACBE6WQNGVSVNCTW,ACBE6WQNGWJHNNGU,ACBE6WQNGZHFABBY,ACBE6WQNH8H9N94Z,ACBE6WQNH9FYZXNR,ACBE6WQNHAPPDSUZ,ACBE6WQNHCMGBDQT,ACBE6WQNHCU2ZZTW,ACBE6WQNHE4ZJCYQ,ACBE6WQNHEJCZZXQ,ACBE6WQNHEMDZFZ9,ACBE6WQNHGN76HGG,ACBE6WQNHGUNHEZT,ACBE6WQNHHCG6SB6,ACBE6WQNHHMMTEFW,ACBE6WQNHJBHBPHM,ACBE6WQNHM2KPUT3,ACBE6WQNHMZDGDPQ,ACBE6WQNHPCGAMBK,ACBE6WQNHTQXQFXV,ACBE6WQNHVBDFPFD,ACBE6WQNHXGZX8HJ,ACBE6WQNHZKNYZZG,ACBE6WQNJ264JYHV,ACBE6WQNJBSKQHRA,ACBE6WQNJD9UEPUX,ACBE6WQNJGKRDTRK,ACBE6WQNJKXZ3KRJ,ACBE6WQNJKZCQ7GR,ACBE6WQNJPEDRZYS,ACBE6WQNJVG8Z57V,ACBE6WQNJVKGPZRK,ACBE6WQNJVKNVTEC,ACBE6WQNJVZS3BAF,ACBE6WQNJZTHNA35,ACBE6WQNKGH6AWCR,ACBE6WQNKSAZTAHV,ACBE6WQNKTAX7MGY,ACBE6WQNKUHBUBNR,ACBE6WQNKUNGNFGQ,ACBE6WQNKWSQMYCX,ACBE6WQNKYRF2GZK,ACBE6WQNM54KFXFR,ACBE6WQNM7PPTSMU,ACBE6WQNMACPCQYS,ACBE6WQNMAGCZNEQ,ACBE6WQNMEUMVUMD,ACBE6WQNMH6UFZBN,ACBE6WQNMHUMN5ZX,ACBE6WQNMMT26PG7,ACBE6WQNMV8NP5DV,ACBE6WQNMVQD7T7H,ACBE6WQNMZHZZFGX,ACBE6WQNMZZQ8GKA,ACBE6WQNN4ZY9Y6V,ACBE6WQNNAXPNC2N,ACBE6WQNNBMHKQ7Z,ACBE6WQNNBTJFAZV,ACBE6WQNNCTGVXPM,ACBE6WQNNHK8AADF,ACBE6WQNNHMK4HEA,ACBE6WQNNHNCXS9Z,ACBE6WQNNKFTATVQ,ACBE6WQNNND394AS,ACBE6WQNNU3B5YE6,ACBE6WQNNVDFZDFJ,ACBE6WQNNVGTZ9YU,ACBE6WQNNYZA6RJY,ACBE6WQNPG5RZY4W,ACBE6WQNPGEQEGT3,ACBE6WQNPGRJYN48,ACBE6WQNPVEZYEDJ,ACBE6WQNPVFNK4C3,ACBE6WQNQFMUAVYT,ACBE6WQNQKYSSYWR,ACBE6WQNQPGAEGCV,ACBE6WQNQXEXEHBQ,ACBE6WQNQXK8NSWF,ACBE6WQNQXXZM7GG,ACBE6WQNQYEGWUNE,ACBE6WQNQZMMJH4V,ACBE6WQNR5SZYFPE,ACBE6WQNRBYXUZHB,ACBE6WQNRDXHYTDM,ACBE6WQNRFHRGCYK,ACBE6WQNRFX9ZPTN,ACBE6WQNRHUDZA3C,ACBE6WQNRKTGNFKJ,ACBE6WQNRKY5A8A2,ACBE6WQNRNUNBGMC,ACBE6WQNRPQZUMVQ,ACBE6WQNRUHWUEWG,ACBE6WQNRVNKUUET,ACBE6WQNRXYMXT5U,ACBE6WQNRZATDPDP,ACBE6WQNRZEFSSTB,ACBE6WQNRZNQHK7F,ACBE6WQNSADXFFHX,ACBE6WQNSAZZ5JR7,ACBE6WQNSBHZ4S3V,ACBE6WQNSEGUBD5G,ACBE6WQNSGWGAGXG,ACBE6WQNSH6ZPVAM,ACBE6WQNSWNURZDJ,ACBE6WQNSXYGGZGK,ACBE6WQNTDVPZA54,ACBE6WQNTH4Y9ZYG,ACBE6WQNTHX95MBZ,ACBE6WQNTN9JJQZW,ACBE6WQNTUUCNPJC,ACBE6WQNTWB7MKEM,ACBE6WQNTYMHDEAS,ACBE6WQNU65HWPGN,ACBE6WQNUGNUG4X8,ACBE6WQNUK9FDJMZ,ACBE6WQNUPMYGUJH,ACBE6WQNUSZR2EQS,ACBE6WQNUYMRD2FB,ACBE6WQNV5HYHCHB,ACBE6WQNV62UYP4E,ACBE6WQNVEQWWFTY,ACBE6WQNVFZCHP7A,ACBE6WQNVJATSHGZ,ACBE6WQNVS6SQSXW,ACBE6WQNVSBRG2JN,ACBE6WQNVUXNPSUV,ACBE6WQNVZPWY2FW,ACBE6WQNW6F5WHV5,ACBE6WQNWEJQKGMU,ACBE6WQNWR4ZRBTG,ACBE6WQNWSVDHJQY,ACBE6WQNWTGFP4Z4,ACBE6WQNWW2HQVGV,ACBE6WQNWWUZFVFH,ACBE6WQNX4RHFHTH,ACBE6WQNX5QGF85X,ACBE6WQNXTTAG2HS,ACBE6WQNXW3EGFCZ,ACBE6WQNXWBGUVQQ,ACBE6WQNXYYU9NSG,ACBE6WQNY2GYC9FJ,ACBE6WQNY6ETGN3H,ACBE6WQNYAGSJMZC,ACBE6WQNYDAZZNWH,ACBE6WQNYDHA5QA4,ACBE6WQNYDMCDUPM,ACBE6WQNYE4EQ5RE,ACBE6WQNYEUEHE65,ACBE6WQNYGEW3PGH,ACBE6WQNYGFV93E3,ACBE6WQNYGZTKHQG,ACBE6WQNYHYVFAYF,ACBE6WQNYJZMZK2N,ACBE6WQNYRNK98MJ,ACBE6WQNYRX82SZS,ACBE6WQNYUGVWHET,ACBE6WQNYVZBKNUK,ACBE6WQNYWPTYMT9,ACBE6WQNYX9KZ44B,ACBE6WQNYYP7ZAHT,ACBE6WQNYYZXTEGD,ACBE6WQNZ6HM9AVX,ACBE6WQNZBGHNFSH,ACBE6WQNZCTZZMBC,ACBE6WQNZDAPE2JK,ACBE6WQNZDUGS2GS,ACBE6WQNZGGFEZBK,ACBE6WQNZGJSP8TR,ACBE6WQNZHGGUPWJ,ACBE6WQNZHPMGHNY,ACBE6WQNZHV7X9RT,ACBE6WQNZHVRVSTN,ACBE6WQNZJQXVYMC,ACBE6WQNZKYAER6F,ACBE6WQNZMQ8CWDU,ACBE6WQNZN9898TC,ACBE6WQNZQHUFTUX,ACBE6WQNZQKGJZXZ,ACBE6WQNZQMSBZU8,ACBE6WQNZRBPTGEY,ACBE6WQNZRH6H7GN,ACBE6WQNZRKDUVUA,ACBE6WQNZTJKM2ZD,ACBE6WQNZTNPZVNS,ACBE6WQNZTZW5UJK,ACBE6WQNZUCDXZV8,ACBE6WQNZYFPHUV5\");";
		transform.call_proc(d);*/
		
		/*String s = "{\"category_name\":\"womens_clothing\",\"categoryPath\":\"Apparels>Women>Lingerie & Sleepwear>Bras\",\"name\":\"ABELINO by ABELINO Girls, Womens Full Coverage Lightly Padded Bra(Purple, Red)\",\"description\":\"This Abelino bra has lightly padded,non wired cups,elasticated & adjustable starps,with extra transparent strap,hook-and-eye closure on the back.\",\"regular_price\":999.0,\"sale_price\":531.0,\"stock\":false,\"image\":\"https://rukminim1.flixcart.com/image/800/800/jh6l2fk0/bra/u/h/g/28b-abelriyapurplered-abelino-original-imaf59fu38rhahuk.jpeg?q=90\",\"product_url\":\"https://www.flipkart.com/abelino-girl-s-women-s-full-coverage-lightly-padded-bra/p/itmf59ftk3geybze?pid=BRAF59BF8NZQJCYN&affid=instantweb\",\"product_id\":\"1_BRAF59BF8NZQJCYN\",\"productFamily\":[\"BRAF59BF33UMUH6A\",\"BRAF59BF3QMQ9UHG\",\"BRAF59BF7JFGF22W\",\"BRAF59BF7ZQ2GXZR\",\"BRAF59BF8RA22GE3\",\"BRAF59BFABGWVCJU\",\"BRAF59BFDCNYKHGK\",\"BRAF59BFGWUDDAPB\",\"BRAF59BFHSYYSGHC\",\"BRAF59BFHYXPJP6T\",\"BRAF59BFKQTJDHWK\",\"BRAF59BFNEYZAM3D\",\"BRAF59BFNSPZFVZT\",\"BRAF59BFQFPWKUVQ\",\"BRAF59BFRKZ9G9FC\",\"BRAF59BFTDZVNYZ5\",\"BRAF59BFUSYEETHK\",\"BRAF59BFUXXJHXRR\",\"BRAF59BFVZDCSATR\",\"BRAF59BFZFTFDRVY\"],\"highlights\":[\"Wirefree\",\"Regular Strap\",\"Without Detachable Straps\",\"Pattern: Solid\",\"Pack of 2\"]}";
		String r=transform.doCatStore(s);
		System.out.println(r);*/
		
		String s = "{\"active_id\":\"SFFDN8UDMGZHQHHX\",\"product_id\":\"1_SFFDN8UDMGZHQHHX\",\"size\":\"6\",\"color\":\"Black and Red\",\"storage\":\"\",\"sizeUnit\":\"UK/India\",\"displaySize\":\"\",\"productUrl\":\"https://www.flipkart.com/sparx-sfg-2006-slippers/p/itmfafyd4f9zxnxh?pid=SFFDN8UDMGZHQHHX&affid=instantweb\",\"inStock\":false,\"specialPrice\":674.0}";
		String r = transform.doAttrStore(s);
		System.out.println(r);
	}
}
