package me.kollcaku.QuarantineFriends.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HobbyReportRowDTO {
    private String hobby;

    @Override
    public String toString() {
        return "HobbyReportRowDTO{" +
                "hobby='" + hobby + '\'' +
                ", count=" + count +
                '}';
    }

    private Long count;
}
