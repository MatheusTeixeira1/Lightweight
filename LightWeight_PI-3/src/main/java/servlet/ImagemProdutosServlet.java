package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/imagens-produtos/*")
public class ImagemProdutosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String BASE_PATH = "C:\\imagens\\imagens-produto"; // Altere para o caminho do seu diretório externo

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedImage = request.getPathInfo().substring(1); // Obtém o nome da imagem da URL
        File imageFile = new File(BASE_PATH, requestedImage);

        if (!imageFile.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404 se a imagem não for encontrada
            return;
        }

        // Define o tipo de conteúdo apropriado
        response.setContentType(getServletContext().getMimeType(imageFile.getName()));
        response.setContentLength((int) imageFile.length());

        // Transmite a imagem para o cliente
        try (FileInputStream in = new FileInputStream(imageFile);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
