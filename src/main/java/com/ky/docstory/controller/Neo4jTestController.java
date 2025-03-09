package com.ky.docstory.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jTemplate;

@RestController
@RequestMapping("/neo4j")
public class Neo4jTestController {

    @Autowired
    private Neo4jTemplate neo4jTemplate;

    @GetMapping("/test")
    public String testNeo4jConnection() {
        try {
            // 간단한 Cypher 쿼리 실행 (노드 개수 확인)
            long count = neo4jTemplate.count("MATCH (n) RETURN count(n)");
            return "Neo4j 연결 성공! 현재 저장된 노드 개수: " + count;
        } catch (Exception e) {
            return "Neo4j 연결 실패: " + e.getMessage();
        }
    }
}
