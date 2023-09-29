package com.springBoard.board.model;

import java.util.HashMap;
import java.util.List;

public class Board {
    public static HashMap<String, String> boardUrlMap = new HashMap<>();

    private Long id;
    private String name;
    private String urlPath;
    private String permission;

    public Board(Long id, String name, String urlPath, String permission) {
        this.id = id;
        this.name = name;
        this.urlPath = urlPath;
        this.permission = permission;
    }

    public static void initBoardUrlMap(List<Board> boards){
        for(Board board : boards){
            boardUrlMap.put(board.getUrlPath(), board.getPermission());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", permission='" + permission + '\'' +
                '}';
    }
}
