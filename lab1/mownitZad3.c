#include <stdio.h>
#include <gsl/gsl_ieee_utils.h>

int main(){
  float a = 4.2;
  while (a > 0){
    gsl_ieee_printf_float(&a); printf("\n");
    a /= 2.0;
  }
  gsl_ieee_printf_float(&a); printf("\n"); //we make sure `a` is zero
  return 0;
}
