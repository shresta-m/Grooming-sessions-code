package com.example.demo.week4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureq3 {

    public static Integer sumOne() throws InterruptedException, ExecutionException{
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(()->{
            int sum =0;
            for(int i=1;i<=10;i++){
                sum+=i;
            }
            return sum;
        });
        return result.get();
    }

    public static Integer sumTwo() throws InterruptedException, ExecutionException{
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(()->{
            int sum =0;
            for(int i=11;i<=20;i++){
                sum+=i;
            }
            return sum;
        });
        return result.get();
    }

    public static Integer sumThree() throws InterruptedException, ExecutionException{
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(()->{
            int sum =0;
            for(int i=21;i<=30;i++){
                sum+=i;
            }
            return sum;
        });
        return result.get();
    }

    public static void main(String args[]){

        try{
            int result = sumOne()+sumTwo()+sumThree();
            System.out.println("The sum from all 3 completable future results is "+result);

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    
}
