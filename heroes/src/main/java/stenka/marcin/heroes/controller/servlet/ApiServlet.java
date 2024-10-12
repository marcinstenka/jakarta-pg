package stenka.marcin.heroes.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import stenka.marcin.heroes.controller.servlet.exception.BadRequestException;
import stenka.marcin.heroes.controller.servlet.exception.NotFoundException;
import stenka.marcin.heroes.unit.controller.api.UnitController;
import stenka.marcin.heroes.user.controller.api.UserController;
import stenka.marcin.heroes.user.dto.PatchUserRequest;
import stenka.marcin.heroes.user.dto.PutUserRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {
    private UserController userController;

    private UnitController unitController;

    private String avatarPath;

    public static final class Paths {
        public static final String API = "/api";
    }

    public static final class Patterns {
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        public static final Pattern USER = Pattern.compile("/users/(%s)".formatted(UUID.pattern()));

        public static final Pattern USERS = Pattern.compile("/users/?");

        public static final Pattern USER_UNITS = Pattern.compile("/users/(%s)/units/?".formatted(UUID.pattern()));

        public static final Pattern USER_AVATAR = Pattern.compile("/users/(%s)/avatar".formatted(UUID.pattern()));
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        userController = (UserController) getServletContext().getAttribute("userController");
        avatarPath = (String) getServletContext().getInitParameter("avatars-upload");
        System.out.println(avatarPath);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USERS.pattern())) {
                response.setContentType("application/json");
                try {
                    response.getWriter().write(jsonb.toJson(userController.getUsers()));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.USER.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    response.getWriter().write(jsonb.toJson(userController.getUser(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.USER_UNITS.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_UNITS, path);
                try {
                    response.getWriter().write(jsonb.toJson(unitController.getUserUnits(uuid)));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                try {
                    byte[] avatar = userController.getUserAvatar(uuid, avatarPath);
                    response.setContentLength(avatar.length);
                    response.getOutputStream().write(avatar);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    userController.putUser(uuid, jsonb.fromJson(request.getReader(), PutUserRequest.class));
                    response.addHeader("Location", createUrl(request, Paths.API, "users", uuid.toString()));
                } catch (BadRequestException ex) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }

                return;
            } else if (path.matches(Patterns.USER_AVATAR.pattern())) {
                response.setContentType("image/png");
                UUID uuid = extractUuid(Patterns.USER_AVATAR, path);
                try {
                    userController.putUserAvatar(uuid, request.getPart("avatar").getInputStream(), avatarPath);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    userController.deleteUser(uuid);
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.USER.pattern())) {
                UUID uuid = extractUuid(Patterns.USER, path);
                try {
                    userController.patchUser(uuid, jsonb.fromJson(request.getReader(), PatchUserRequest.class));
                } catch (NotFoundException ex) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }


    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }


    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }


}
