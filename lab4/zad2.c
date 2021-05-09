#include <stdio.h>
#include <stdlib.h>

#include <time.h>
#include <sys/times.h>
#include <unistd.h>

#include <gsl/gsl_blas.h>


#define NO_TESTS 5
#define NO_REPETITIONS 10
#define ARR_LEN NO_TESTS*NO_REPETITIONS*3 


void save_measurement(int *size_arr, double *time_arr, int *type_arr, int n, int start_time, int end_time, int type, int no_measurement){
    int ticks_per_sec = CLOCKS_PER_SEC;
    
    size_arr[no_measurement] = n;
    time_arr[no_measurement] = (double)(end_time - start_time) / ticks_per_sec;
    type_arr[no_measurement] = type;
}


//functions for maintaing arrays
double **zeros2d(int n){
    double **result = (double**) calloc(n, sizeof(double*));
    for (int i = 0; i < n; i++){
        result[i] = (double*) calloc(n, sizeof(double));
    }
    return result;
}

void setRandom2d(double** arr, int n){   
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            arr[i][j] = 0;
        }
    }  
}

void free2d_double(double** arr, int n){
    for (int i = 0; i < n; i++){
        free(arr[i]);
    }
    free(arr);
}


void setZero2d(double** arr, int n){   
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            arr[i][j] = 0;
        }
    }  
}


void print2d(double** arr, int n){
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            printf("%lf ", arr[i][j]);
        }
        printf("\n");
    }
    printf("\n");
}



void print2d_linear(double* arr, int n){
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            printf("%lf ", arr[i*n + j]);
        }
        printf("\n");
    }
    printf("\n");
}


double* linearize_matrix(double** arr, int n){
    double* result = calloc(n*n, sizeof(double));
    for (int i = 0; i < n; i++){
        for (int j = 0; j < n; j++){
            result[i*n + j] = arr[i][j];
        }
    }
    return result;
}


//zakładamy, że macierz c jest wyzerowana
void naive_matrix_multiplication(double** a, double** b, double** c, int n){  
    for (int j = 0; j < n; j++){
        for (int i = 0; i < n; i++){
            for (int k = 0; k < n; k++){
                c[i][j] += a[i][k] * b[k][j]; 
            }
        }
    }
}

void better_matrix_multiplication(double** a, double** b, double** c, int n){
    for (int i = 0; i < n; i++){
        for (int k = 0; k < n; k++){
            for (int j = 0; j < n; j++){
                c[i][j] += a[i][k] * b[k][j]; 
            }
        }
    }
}


int main(void){
    srand(time(NULL));
    
    char path[] = "C_multiplication.csv";
    FILE* f_stream = fopen(path, "w");
    int ns[NO_TESTS] = {100, 200, 300, 400, 500};


    double **a;
    double **b;
    double **c;

    double *linear_a;
    double *linear_b;
    double *linear_c;

    gsl_matrix_view A;
    gsl_matrix_view B;
    gsl_matrix_view C;

    int size[ARR_LEN];
    double time[ARR_LEN];
    int type[ARR_LEN];

    int start, end;


    for (int i = 0; i < NO_TESTS; i++){
        int n = ns[i];
        a = zeros2d(n);
        b = zeros2d(n);
        c = zeros2d(n);

        printf("n=%d\n", n);
        for (int j = 0; j < NO_REPETITIONS; j++){
            setRandom2d(a, n);
            setRandom2d(b, n);

            start = clock();
            naive_matrix_multiplication(a, b, c, n);
            end = clock();
            save_measurement(size, time, type, n, start, end, 1, 3*(i*NO_REPETITIONS + j));

            setZero2d(c, n);

            start = clock();
            better_matrix_multiplication(a, b, c, n);
            end = clock();
            save_measurement(size, time, type, n, start, end, 2, 3*(i*NO_REPETITIONS + j) + 1);

            setZero2d(c, n);

            linear_a = linearize_matrix(a, n);
            linear_b = linearize_matrix(b, n);
            linear_c = linearize_matrix(c, n);

            A = gsl_matrix_view_array(linear_a, n, n);
            B = gsl_matrix_view_array(linear_b, n, n);
            C = gsl_matrix_view_array(linear_c, n, n);

            start = clock();
            gsl_blas_dgemm (CblasNoTrans, CblasNoTrans,
                    1.0, &A.matrix, &B.matrix,
                    0.0, &C.matrix);
            end = clock();
            save_measurement(size, time, type, n, start, end, 3, 3*(i*NO_REPETITIONS + j) + 2);

            setZero2d(c, n);

            printf("iter = %d\n", j);
            free(linear_a);
            free(linear_b);
            free(linear_c);
        }

        printf("\n");
        free2d_double(a, n);
        free2d_double(b, n);
        free2d_double(c, n);
    }

    fprintf(f_stream, "Rozmiar;Czas;Typ\n");
    for (int i = 0; i < ARR_LEN; i++){
        fprintf(f_stream, "%d;%f;%d\n", size[i], time[i], type[i]);
    }
    fclose(f_stream);
}

