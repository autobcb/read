((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={
cpO(d,e,f){if(d<=0)return new B.lQ(f.i("lQ<0>"))
return new A.a_U(d,e,f.i("a_U<0>"))},
a_U:function a_U(d,e,f){this.a=d
this.b=e
this.$ti=f},
csa(d){var w
if(d==null)w=D.V6
else{w=new A.bBk()
w.aHi(d)}return w},
bvz:function bvz(){},
bBk:function bBk(){this.b=this.a=0},
Nw:function Nw(d,e,f){this.a=d
this.b=e
this.c=f},
DT:function DT(d,e,f){this.a=d
this.b=e
this.$ti=f}},D
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[110],A)
D=c[178]
A.a_U.prototype={
cR(d,e){B.bXw(e,this.a,this,null,null)
return this.b.$1(e)},
gD(d){return this.a}}
A.bvz.prototype={
KE(d){if(d<=0||d>4294967296)throw B.k(B.iY(y.c+d))
return Math.random()*d>>>0}}
A.bBk.prototype={
aHi(d){var w,v,u,t,s,r,q,p=this,o=4294967296
do{w=d>>>0
d=C.f.b8(d-w,o)
v=d>>>0
d=C.f.b8(d-v,o)
u=(~w>>>0)+(w<<21>>>0)
t=u>>>0
v=(~v>>>0)+((v<<21|w>>>11)>>>0)+C.f.b8(u-t,o)>>>0
u=((t^(t>>>24|v<<8))>>>0)*265
w=u>>>0
v=((v^v>>>24)>>>0)*265+C.f.b8(u-w,o)>>>0
u=((w^(w>>>14|v<<18))>>>0)*21
w=u>>>0
v=((v^v>>>14)>>>0)*21+C.f.b8(u-w,o)>>>0
w=(w^(w>>>28|v<<4))>>>0
v=(v^v>>>28)>>>0
u=(w<<31>>>0)+w
t=u>>>0
s=C.f.b8(u-t,o)
u=p.a*1037
r=p.a=u>>>0
q=p.b*1037+C.f.b8(u-r,o)>>>0
p.b=q
r=(r^t)>>>0
p.a=r
s=(q^v+((v<<31|w>>>1)>>>0)+s>>>0)>>>0
p.b=s}while(d!==0)
if(s===0&&r===0)p.a=23063
p.Bv()
p.Bv()
p.Bv()
p.Bv()},
Bv(){var w=this,v=w.a,u=4294901760*v,t=u>>>0,s=55905*v,r=s>>>0,q=r+t+w.b
v=q>>>0
w.a=v
w.b=C.f.b8(s-r+(u-t)+(q-v),4294967296)>>>0},
KE(d){var w,v,u,t=this
if(d<=0||d>4294967296)throw B.k(B.iY(y.c+d))
w=d-1
if((d&w)===0){t.Bv()
return(t.a&w)>>>0}do{t.Bv()
v=t.a
u=v%d}while(v-u+d>=4294967296)
return u}}
A.Nw.prototype={
gu(d){var w=this.a
return 3*w.a.ij(0,this.b)+7*w.b.ij(0,this.c)&2147483647},
k(d,e){var w
if(e==null)return!1
if(e instanceof A.Nw){w=this.a
w=w.a.fA(this.b,e.b)&&w.b.fA(this.c,e.c)}else w=!1
return w},
gc9(d){return this.b},
gn(d){return this.c}}
A.DT.prototype={
fA(d,e){var w,v,u,t,s,r,q
if(d===e)return!0
w=J.M(d)
v=J.M(e)
if(w.gD(d)!==v.gD(e))return!1
u=B.kE(null,null,null,x.b,x.e)
for(t=J.bi(w.gd7(d));t.C();){s=t.gT(t)
r=new A.Nw(this,s,w.h(d,s))
q=u.h(0,r)
u.p(0,r,(q==null?0:q)+1)}for(w=J.bi(v.gd7(e));w.C();){s=w.gT(w)
r=new A.Nw(this,s,v.h(e,s))
q=u.h(0,r)
if(q==null||q===0)return!1
u.p(0,r,q-1)}return!0},
ij(d,e){var w,v,u,t,s,r,q,p,o
for(w=J.d0(e),v=J.bi(w.gd7(e)),u=this.a,t=this.b,s=this.$ti.y[1],r=0;v.C();){q=v.gT(v)
p=u.ij(0,q)
o=w.h(e,q)
r=r+3*p+7*t.ij(0,o==null?s.a(o):o)&2147483647}r=r+(r<<3>>>0)&2147483647
r^=r>>>11
return r+(r<<15>>>0)&2147483647}}
var z=a.updateTypes([]);(function inheritance(){var w=a.inherit,v=a.inheritMany
w(A.a_U,B.aC)
v(B.w,[A.bvz,A.bBk,A.Nw,A.DT])})()
B.cP(b.typeUniverse,JSON.parse('{"a_U":{"aC":["1"],"b_":["1"],"J":["1"],"J.E":"1","aC.E":"1"}}'))
var y={c:"max must be in range 0 < max \u2264 2^32, was "}
var x={b:B.D("Nw"),e:B.D("r")};(function constants(){D.V6=new A.bvz()})()};
(a=>{a["ZJFDCh3c6+wZ62Sn7dS8Hjqj8w4="]=a.current})($__dart_deferred_initializers__);