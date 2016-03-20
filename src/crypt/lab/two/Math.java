package crypt.lab.two;

public class Math {
    public int gcd(int a, int b){
        return (b==0) ? a : gcd(b, a%b);
    }
    public int[] equationSolver(int a,int b, QuardMatrix E){
        int q=0;
        int r=a%b;
        if (r==0){
            int [][] m=E.getM();
            return new int []{m[0][1],m[1][1]};
        }else{
            q=(a-r)/b;
            E=E.multiply(new QuardMatrix(0,1,1,-q));
            return equationSolver(b,r,E);
        }
    }
    public int reverse(int a,int m){
        int d=gcd(a,m);
        if (d==1){
            QuardMatrix matrix=new QuardMatrix(1,0,0,1);
            int res[]=equationSolver(a,m,matrix);
            if (res[0]<0){
                res[0]+=m;
            }
            return res[0];
        }
        else{
            System.out.println("d="+d);
            return 0;
        }
    }
}
