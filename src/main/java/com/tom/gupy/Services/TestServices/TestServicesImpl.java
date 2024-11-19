package com.tom.gupy.Services.TestServices;

import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.tom.gupy.Models.TestsModel.Test;
import com.tom.gupy.Models.TestsModel.TestItems;
import com.tom.gupy.Models.TestsModel.TestResult;
import com.tom.gupy.Models.TestsModel.TestResultItem;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class TestServicesImpl implements TestServices {
    private final OkHttpClient client = new OkHttpClient();

    @Override
    public TestItems RecuperarTestes(Integer limit, Integer offset) {
        try {
            List<Test> tests = new ArrayList<>();
            JSONArray resultados = fetchExams(limit, offset);
            // Converter JSONArray para List<JSONObject>
            List<JSONObject> resultadoList = IntStream.range(0, resultados.length())
                    .mapToObj(resultados::getJSONObject)
                    .toList();
            // Filtrar testes que contenham "Gupy" no nome
            List<JSONObject> testsGupy = resultadoList.stream()
                    .filter(item -> item.getString("name").contains("Gupy"))
                    .toList();
            // Converter cada JSONObject em Test e adicionar à lista de testes
            testsGupy.forEach(test ->
                    tests.add(new Test(test.getString("id"), test.getString("name")))
            );
            // Criar e retornar o objeto TestItems
            return new TestItems(limit, offset, tests.size(), tests);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao recuperar testes: " + e.getMessage(), e);
        }
    }

    @Override
    public ResponseEntity<TestResult> PegarResultadosDoUsuarioID(String idResult) {
        try{
            Request request = new Request.Builder()
                    .url("http://app.lizeedu.com.br/api/v2/application-students-results/")
                    .get()
                    .addHeader("Content-Type","application/json")
                    .addHeader("Authorization", "Token SEU TOKEN LIZE AQUI")
                    .build();
            Response response = client.newCall(request).execute();
            JSONObject jsonData = new JSONObject(response.body().string());
            JSONArray jsonResults = new JSONArray(jsonData.getJSONArray("results"));

            TestResult testResult = EncontrarResultado(idResult, jsonResults);
          return ResponseEntity.ok(testResult);
        }catch (Exception e){
            throw new RuntimeException("Erro ao buscar Resultados. Error: "+e.getMessage());
        }
    }

    private TestResult EncontrarResultado(String idResult, JSONArray jsonResults) {
        String[] idExameEidUser = idResult.split("&");
        List<JSONObject> results = IntStream.range(0, jsonResults.length())
                .mapToObj(jsonResults::getJSONObject)
                .toList();

        // Filtra os resultados que correspondem ao exam_id e student_id
        List<JSONObject> resultadosTesteLize = results.stream().filter(result ->
                result.getString("exam_id").equals(idExameEidUser[0]) &&
                        result.getString("student_id").equals(idExameEidUser[1])
        ).toList();

        // Inicializa a lista de itens de resultados
        List<TestResultItem> testResultItemList = new ArrayList<>();

        // Se houve algum resultado encontrado, processa-o
        if (!resultadosTesteLize.isEmpty()) {
            resultadosTesteLize.forEach(test -> {
                testResultItemList.add(
                        TestResultItem.builder()
                                .score(test.getInt("grade"))
                                .type_result("percentage")
                                .title(test.getString("exam"))
                                .tier(test.getInt("grade") > 50 ? "major" : "minor")
                                .build()
                );
            });

            // Retorna o TestResult construído
            return TestResult.builder()
                    .title(resultadosTesteLize.get(0).getString("exam"))
                    .testCode(resultadosTesteLize.get(0).getString("subject"))
                    .description("")  // Descrição pode ser preenchida conforme necessário
                    .providerName("LizeEdu")
                    .providerLink("https://app.lizeedu.com.br/")
                    .company_result_string(resultadosTesteLize.get(0).get("grade").toString())
                    .results(testResultItemList)
                    .build();
        }
        // Caso nenhum resultado seja encontrado, retorna null ou lança uma exceção
        return null;
    }


    // Metodo separado para fazer a chamada à API e retornar o JSONArray com os resultados
    private JSONArray fetchExams(Integer limit, Integer offset) throws Exception {
        Request req = new Request.Builder()
                .url("https://app.lizeedu.com.br/api/v2/exams/?limit=" + limit + "&offset=" + offset)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Token SEU TOKEN LIZE AQUI")
                .build();

        Response response = client.newCall(req).execute();
        if (!response.isSuccessful()) {
            throw new RuntimeException("Falha na requisição: " + response);
        }

        JSONObject data = new JSONObject(response.body().string());
        return data.getJSONArray("results");
    }
}
