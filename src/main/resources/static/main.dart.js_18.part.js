((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={
cqf(d,e,f){if(d<=0)return new B.lR(f.i("lR<0>"))
return new A.a_Y(d,e,f.i("a_Y<0>"))},
a_Y:function a_Y(d,e,f){this.a=d
this.b=e
this.$ti=f},
csC(d){var w
if(d==null)w=D.V7
else{w=new A.bBt()
w.aHE(d)}return w},
bvH:function bvH(){},
bBt:function bBt(){this.b=this.a=0},
Ny:function Ny(d,e,f){this.a=d
this.b=e
this.c=f},
DU:function DU(d,e,f){this.a=d
this.b=e
this.$ti=f}},D
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[110],A)
D=c[178]
A.a_Y.prototype={
cR(d,e){B.bXQ(e,this.a,this,null,null)
return this.b.$1(e)},
gD(d){return this.a}}
A.bvH.prototype={
KH(d){if(d<=0||d>4294967296)throw B.k(B.j_(y.c+d))
return Math.random()*d>>>0}}
A.bBt.prototype={
aHE(d){var w,v,u,t,s,r,q,p=this,o=4294967296
do{w=d>>>0
d=C.f.b4(d-w,o)
v=d>>>0
d=C.f.b4(d-v,o)
u=(~w>>>0)+(w<<21>>>0)
t=u>>>0
v=(~v>>>0)+((v<<21|w>>>11)>>>0)+C.f.b4(u-t,o)>>>0
u=((t^(t>>>24|v<<8))>>>0)*265
w=u>>>0
v=((v^v>>>24)>>>0)*265+C.f.b4(u-w,o)>>>0
u=((w^(w>>>14|v<<18))>>>0)*21
w=u>>>0
v=((v^v>>>14)>>>0)*21+C.f.b4(u-w,o)>>>0
w=(w^(w>>>28|v<<4))>>>0
v=(v^v>>>28)>>>0
u=(w<<31>>>0)+w
t=u>>>0
s=C.f.b4(u-t,o)
u=p.a*1037
r=p.a=u>>>0
q=p.b*1037+C.f.b4(u-r,o)>>>0
p.b=q
r=(r^t)>>>0
p.a=r
s=(q^v+((v<<31|w>>>1)>>>0)+s>>>0)>>>0
p.b=s}while(d!==0)
if(s===0&&r===0)p.a=23063
p.Bu()
p.Bu()
p.Bu()
p.Bu()},
Bu(){var w=this,v=w.a,u=4294901760*v,t=u>>>0,s=55905*v,r=s>>>0,q=r+t+w.b
v=q>>>0
w.a=v
w.b=C.f.b4(s-r+(u-t)+(q-v),4294967296)>>>0},
KH(d){var w,v,u,t=this
if(d<=0||d>4294967296)throw B.k(B.j_(y.c+d))
w=d-1
if((d&w)===0){t.Bu()
return(t.a&w)>>>0}do{t.Bu()
v=t.a
u=v%d}while(v-u+d>=4294967296)
return u}}
A.Ny.prototype={
gu(d){var w=this.a
return 3*w.a.im(0,this.b)+7*w.b.im(0,this.c)&2147483647},
k(d,e){var w
if(e==null)return!1
if(e instanceof A.Ny){w=this.a
w=w.a.fz(this.b,e.b)&&w.b.fz(this.c,e.c)}else w=!1
return w},
gc9(d){return this.b},
gn(d){return this.c}}
A.DU.prototype={
fz(d,e){var w,v,u,t,s,r,q
if(d===e)return!0
w=J.M(d)
v=J.M(e)
if(w.gD(d)!==v.gD(e))return!1
u=B.kF(null,null,null,x.b,x.e)
for(t=J.bj(w.gd7(d));t.C();){s=t.gU(t)
r=new A.Ny(this,s,w.h(d,s))
q=u.h(0,r)
u.p(0,r,(q==null?0:q)+1)}for(w=J.bj(v.gd7(e));w.C();){s=w.gU(w)
r=new A.Ny(this,s,v.h(e,s))
q=u.h(0,r)
if(q==null||q===0)return!1
u.p(0,r,q-1)}return!0},
im(d,e){var w,v,u,t,s,r,q,p,o
for(w=J.d0(e),v=J.bj(w.gd7(e)),u=this.a,t=this.b,s=this.$ti.y[1],r=0;v.C();){q=v.gU(v)
p=u.im(0,q)
o=w.h(e,q)
r=r+3*p+7*t.im(0,o==null?s.a(o):o)&2147483647}r=r+(r<<3>>>0)&2147483647
r^=r>>>11
return r+(r<<15>>>0)&2147483647}}
var z=a.updateTypes([]);(function inheritance(){var w=a.inherit,v=a.inheritMany
w(A.a_Y,B.aC)
v(B.w,[A.bvH,A.bBt,A.Ny,A.DU])})()
B.cQ(b.typeUniverse,JSON.parse('{"a_Y":{"aC":["1"],"b_":["1"],"K":["1"],"K.E":"1","aC.E":"1"}}'))
var y={c:"max must be in range 0 < max \u2264 2^32, was "}
var x={b:B.D("Ny"),e:B.D("r")};(function constants(){D.V7=new A.bvH()})()};
(a=>{a["6/51+q+WfpOZpWxs4KNYADKWEjY="]=a.current})($__dart_deferred_initializers__);