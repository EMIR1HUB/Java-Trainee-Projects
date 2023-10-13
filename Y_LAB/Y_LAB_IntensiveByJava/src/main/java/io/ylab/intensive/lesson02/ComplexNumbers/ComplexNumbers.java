package io.ylab.intensive.lesson02.ComplexNumbers;

public class ComplexNumbers {
    private double real;
    private double imaginary;

    public ComplexNumbers(double real){
        this.real = real;
        this.imaginary = 1;
    }

    public ComplexNumbers(double real, double imaginary){
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }

    public ComplexNumbers plus(ComplexNumbers a){
        double newReal = real + a.getReal();
        double newImaginary = imaginary + a.getImaginary();
        return new ComplexNumbers(newReal, newImaginary);
    }

    public ComplexNumbers minus(ComplexNumbers a){
        double newReal = real - a.getReal();
        double newImaginary = imaginary - a.getImaginary();
        return new ComplexNumbers(newReal, newImaginary);
    }

    public ComplexNumbers multi(ComplexNumbers a){
        double newReal = real * a.getReal() - imaginary * a.getImaginary();
        double newImaginary = imaginary * a.getReal() + real * a.getImaginary();
        return new ComplexNumbers(newReal, newImaginary);
    }

    public double abc(){
        return Math.sqrt(real * real + imaginary * imaginary);
    }

    @Override
    public String toString() {
        return  "real = " + real +
                ", imaginary = " + imaginary;
    }
}
