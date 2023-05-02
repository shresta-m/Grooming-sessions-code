package com.example.demo.week4;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureq3 {

    public static void main(String args[]){

        try{
            CompletableFuture<Integer> result1 = CompletableFuture.supplyAsync(()->{
                int sum =0;
                for(int i=1;i<=10;i++){
                    sum+=i;
                }
                return sum;
            });
            CompletableFuture<Integer> result2 = CompletableFuture.supplyAsync(()->{
                int sum =0;
                for(int i=11;i<=20;i++){
                    sum+=i;
                }
                return sum;
            });
            CompletableFuture<Integer> result3 = CompletableFuture.supplyAsync(()->{
                int sum =0;
                for(int i=21;i<=30;i++){
                    sum+=i;
                }
                return sum;
            });
            CompletableFuture<Integer> finalResult = result1.thenCombine(result2,(a,b)-> a+b)
                    .thenCombine(result3,(ab,c)-> ab+c);
            System.out.println("The sum from all 3 completable future results is "+finalResult.join());

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    
}
