package com.cetide.blog.util;

import com.cetide.blog.model.dto.ArticleWithWeight;
import com.cetide.blog.model.entity.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class AlgorithmUtils {


    @Value("${recommendation.randomness-factor:0.2}")
    private static double randomnessFactor;


    // 计算每篇文章与目标文章的权重（基于相似度）
    public static List<ArticleWithWeight> calculateArticleWeightsWithTarget(List<Article> articles, Article targetArticle) {
        List<ArticleWithWeight> articlesWithWeights = new ArrayList<>();

        for (Article article : articles) {
            // 计算与目标文章的相似度
            double similarity = calculateSimilarityWithTarget(article, targetArticle);
            articlesWithWeights.add(new ArticleWithWeight(article, similarity));
        }

        return articlesWithWeights;
    }

    // 计算一篇文章与目标文章的相似度（可以扩展为更复杂的计算方式）
    private static double calculateSimilarityWithTarget(Article article, Article targetArticle) {
        // 这里你可以使用更复杂的算法来计算相似度，比如基于文本内容的相似度计算
        // 例如，可以计算标题的相似度，或者用词向量计算内容相似度
        // 这里只是一个简单示例，假设相似度计算基于文章标题相似度
        double titleSimilarity = calculateTitleSimilarity(article.getTitle(), targetArticle.getTitle());
        return titleSimilarity;  // 返回计算得到的相似度
    }

    // 计算两篇文章标题的相似度（可以扩展）
    private static double calculateTitleSimilarity(String title1, String title2) {
        // 这里是一个简单的计算方法，实际应用中可以使用 Levenshtein 距离、Jaccard 相似度等算法
        int commonWords = 0;
        String[] words1 = title1.split("\\s+");
        String[] words2 = title2.split("\\s+");

        // 简单地比较标题中相同的单词数量
        for (String word1 : words1) {
            for (String word2 : words2) {
                if (word1.equalsIgnoreCase(word2)) {
                    commonWords++;
                }
            }
        }

        // 返回相似度得分（这里是简单示例，可以根据需求调整）
        return (double) commonWords / Math.max(words1.length, words2.length);
    }










    // 计算每篇文章的权重（基于相似度）
    public static List<ArticleWithWeight> calculateArticleWeights(List<Article> articles) {
        List<ArticleWithWeight> articlesWithWeights = new ArrayList<>();

        for (Article article : articles) {
            // 假设这里的相似度得分是基于标题和内容的长度差异或者某种相似度计算方法
            double similarity = calculateSimilarity(article); // 为每篇文章计算一个相似度得分
            articlesWithWeights.add(new ArticleWithWeight(article, similarity));
        }

        return articlesWithWeights;
    }

    // 计算一篇文章的相似度（简单示例：这里可以扩展为更复杂的计算方式）
    private static double calculateSimilarity(Article article) {
        // 这里只是一个简单示例，实际应用中可以使用 TF-IDF 或者 Word2Vec 之类的方法
        return Math.random();  // 随机生成一个相似度得分（这里仅作为示例）
    }

    // 二分查找函数
    private static int binarySearch(double[] cumulativeWeights, double randomValue) {
        int left = 0, right = cumulativeWeights.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (cumulativeWeights[mid] >= randomValue) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // 更新累计权重数组
    private static double[] updateCumulativeWeights(double[] cumulativeWeights, int index) {
        for (int i = index; i < cumulativeWeights.length - 1; i++) {
            cumulativeWeights[i] = cumulativeWeights[i + 1] - cumulativeWeights[index];
        }
        return Arrays.copyOf(cumulativeWeights, cumulativeWeights.length - 1);
    }

    public static List<Article> selectWeightedRandomArticles(List<ArticleWithWeight> articlesWithWeights, int num) {
        // 计算总权重
        double totalWeight = articlesWithWeights.stream().mapToDouble(ArticleWithWeight::getWeight).sum();

        // 确保总权重大于 0
        if (totalWeight <= 0) {
            totalWeight = 1.0;
        }

        // 基于权重进行加权选择
        List<Article> selectedArticles = new ArrayList<>();
        Random rand = new Random();

        // 计算累计权重列表，避免每次选择都计算权重累加
        List<Double> cumulativeWeights = new ArrayList<>();
        double cumulativeWeight = 0.0;
        for (ArticleWithWeight articleWithWeight : articlesWithWeights) {
            cumulativeWeight += articleWithWeight.getWeight();
            cumulativeWeights.add(cumulativeWeight);
        }

        int selectedCount = 0;
        while (selectedCount < num && !articlesWithWeights.isEmpty()) {
            // 随机选择一个值
            double randomValue = rand.nextDouble() * totalWeight;

            // 二分查找找到应该选择的文章
            // 在调用 binarySearch 时将 List<Double> 转换为 double[]
            int index = binarySearch(cumulativeWeights.stream().mapToDouble(Double::doubleValue).toArray(), randomValue);

            // 选择文章
            selectedArticles.add(articlesWithWeights.get(index).getArticle());

            // 移除已选择的文章
            articlesWithWeights.remove(index);
            cumulativeWeights = updateCumulativeWeights(cumulativeWeights, index);

            selectedCount++;
        }

        return selectedArticles;
    }

    // 更新累计权重列表
    private static List<Double> updateCumulativeWeights(List<Double> cumulativeWeights, int index) {
        cumulativeWeights.remove(index);  // 移除已选择的文章
        for (int i = index; i < cumulativeWeights.size(); i++) {
            cumulativeWeights.set(i, cumulativeWeights.get(i) - cumulativeWeights.get(index));
        }
        return cumulativeWeights;
    }
}
