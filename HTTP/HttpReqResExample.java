import java.net.*;
import java.net.http.*;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;

public class HttpReqResExample {
	public static void main(String[] args) {
		try {
			URI uri = new URI("https://www.geeksforgeeks.org/html-forms/");
			HttpRequest req = HttpRequest.newBuilder(uri).GET().build();
			/*
			 * HttpRequest.newBuilder(uri).POST(
			 * HttpRequest.BodyPublishers.ofString("{\"action\":\"hello\"}")).build();
			 */
			HttpClient client = HttpClient.newHttpClient();

			// we could get the response as a string or write it directly to an html file instead
			// HttpResponse<String> res = client.send(req, BodyHandlers.ofString());
			Path filePath = Path.of("output.html");
			HttpResponse<Path> res = client.send(req, HttpResponse.BodyHandlers.ofFile(filePath));

			System.out.println(res.body());
			System.out.println(res.statusCode());
			/*
			 * uri = new URI("https://api.weather.gov/gridpoints/TOP/32,81/forecast");
			 * req=HttpRequest.newBuilder(uri).GET().build();
			 * res=client.send(req, BodyHandlers.ofString());
			 *
			 * System.out.println(res.body());
			 */
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
