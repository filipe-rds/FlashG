package br.edu.ifpb.pweb2.flashg.dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeDTO {

    private String response;
    private Integer likeCount;

}
