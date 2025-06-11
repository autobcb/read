((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_86",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,G,D,F,H,A={
bTV(d,e,f,g,h){var x=B.ba("hue")
if(g===0)x.b=0
else if(g===d)x.b=60*C.d.aO((e-f)/h,6)
else if(g===e)x.b=60*((f-d)/h+2)
else if(g===f)x.b=60*((d-e)/h+4)
x.b=isNaN(x.aD())?0:x.aD()
return x.aD()},
bTA(d,e,f,g,h){var x,w,v,u,t,s,r
$label0$0:{if(e<60){x=new B.mK(f,g,0)
break $label0$0}if(e<120){x=new B.mK(g,f,0)
break $label0$0}if(e<180){x=new B.mK(0,f,g)
break $label0$0}if(e<240){x=new B.mK(0,g,f)
break $label0$0}if(e<300){x=new B.mK(g,0,f)
break $label0$0}x=new B.mK(f,0,g)
break $label0$0}w=x.a
v=null
u=null
t=x.b
s=x.c
u=s
v=t
r=w
return B.L(C.d.L(d*255),C.d.L((r+h)*255),C.d.L((v+h)*255),C.d.L((u+h)*255))},
bGh(d){var x=d.a,w=(x>>>16&255)/255,v=(x>>>8&255)/255,u=(x&255)/255,t=Math.max(w,Math.max(v,u)),s=Math.min(w,Math.min(v,u)),r=t-s,q=A.bTV(w,v,u,t,r),p=(t+s)/2,o=p===1?0:B.Z(r/(1-Math.abs(2*p-1)),0,1)
return new A.eu((x>>>24&255)/255,q,o,p)},
eu:function eu(d,e,f,g){var _=this
_.a=d
_.b=e
_.c=f
_.d=g},
bR4(d){var x,w,v
switch(d.w.a){case 1:x=d.c
w=x!=null?new D.fA(0,x.gBM(x)):F.ta
break
case 0:x=d.d
w=d.c
if(x!=null){v=w==null?null:w.gBM(w)
w=new D.c6(x,v==null?F.u:v)}else if(w==null)w=E.rD
break
default:w=null}return new A.nA(d.a,d.f,d.b,d.e,w)},
aZR(d,e,f){var x,w,v,u,t,s,r=null
if(d==e)return d
x=d==null
if(!x&&e!=null){if(f===0)return d
if(f===1)return e}w=x?r:d.a
v=e==null
w=D.aa(w,v?r:e.a,f)
u=x?r:d.b
u=D.bOM(u,v?r:e.b,f)
t=x?r:d.c
t=D.bFE(t,v?r:e.c,f)
s=x?r:d.d
s=D.bFk(s,v?r:e.d,f)
x=x?r:d.e
x=D.hd(x,v?r:e.e,f)
x.toString
return new A.nA(w,u,t,s,x)},
cdx(d,e){return new A.apu(d,e)},
nA:function nA(d,e,f,g,h){var _=this
_.a=d
_.b=e
_.c=f
_.d=g
_.e=h},
apu:function apu(d,e){var _=this
_.b=d
_.d=_.c=null
_.e=$
_.w=_.r=_.f=null
_.z=_.y=_.x=$
_.Q=null
_.a=e},
bvU:function bvU(){},
bvV:function bvV(d){this.a=d},
bvW:function bvW(d,e,f){this.a=d
this.b=e
this.c=f},
bGc(d,e,f,g,h,i,j,k,l,m){return new A.Aj(g,h,!1,d,m,k,l,j,i,f,null)},
Aj:function Aj(d,e,f,g,h,i,j,k,l,m,n){var _=this
_.c=d
_.d=e
_.e=f
_.w=g
_.x=h
_.y=i
_.z=j
_.Q=k
_.as=l
_.ax=m
_.a=n},
UV:function UV(d){var _=this
_.f=_.e=_.d=!1
_.r=d
_.c=_.a=null},
beU:function beU(d){this.a=d},
beS:function beS(d){this.a=d},
beN:function beN(d){this.a=d},
beO:function beO(d){this.a=d},
beM:function beM(d,e){this.a=d
this.b=e},
beR:function beR(d){this.a=d},
beP:function beP(d){this.a=d},
beQ:function beQ(d,e){this.a=d
this.b=e},
beT:function beT(d,e){this.a=d
this.b=e},
caa(d){var x=B.J(y.I,y.T)
d.ae(0,new A.aZY(x))
return x},
ad8(d,e,f){return new A.Ce(null,f,d,e,null)},
yf:function yf(d,e){this.a=d
this.b=e},
Hv:function Hv(d,e){var _=this
_.b=d
_.c=null
_.R$=0
_.M$=e
_.aX$=_.aQ$=0},
aZY:function aZY(d){this.a=d},
aZX:function aZX(){},
Ce:function Ce(d,e,f,g,h){var _=this
_.c=d
_.d=e
_.e=f
_.f=g
_.a=h},
Xo:function Xo(){this.c=this.a=this.d=null},
apx:function apx(){},
apy:function apy(){},
arW:function arW(){},
HF:function HF(){},
lG:function lG(){},
qG:function qG(){},
RR:function RR(d,e,f,g,h){var _=this
_.p1=d
_.p2=e
_.c=_.b=_.a=_.CW=_.ay=null
_.d=$
_.e=f
_.r=_.f=null
_.w=g
_.z=_.y=null
_.Q=!1
_.as=!0
_.at=!1
_.$ti=h},
Xx:function Xx(){},
RD(d,e,f){if(e==null)if(d==null)return null
else return d.ag(0,1-f)
else if(d==null)return e.ag(0,f)
else return new B.U(D.r6(d.a,e.a,f),D.r6(d.b,e.b,f))},
bH8(d,e,f,g,h){var x=h.a,w=h.b
return new B.nt(d,e,f,g,x,w,x,w,x,w,x,w,x===w)}},E
J=c[1]
B=c[0]
C=c[2]
G=c[119]
D=c[113]
F=c[122]
H=c[115]
A=a.updateHolder(c[89],A)
E=c[135]
A.eu.prototype={
bh(){var x=this,w=x.d,v=(1-Math.abs(2*w-1))*x.c,u=x.b
return A.bTA(x.a,u,v,v*(1-Math.abs(C.d.aO(u/60,2)-1)),w-v/2)},
l(d,e){var x=this
if(e==null)return!1
if(x===e)return!0
return e instanceof A.eu&&e.a===x.a&&e.b===x.b&&e.c===x.c&&e.d===x.d},
gt(d){var x=this
return B.ar(x.a,x.b,x.c,x.d,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a)},
k(d){var x=this
return"HSLColor("+B.m(x.a)+", "+B.m(x.b)+", "+B.m(x.c)+", "+B.m(x.d)+")"}}
A.nA.prototype={
Jg(d,e){return this.e.fS(d,e)},
gea(d){return this.e.gmF()},
gQi(){return this.d!=null},
eI(d,e){var x
$label0$0:{if(d instanceof D.aK){x=A.aZR(A.bR4(d),this,e)
break $label0$0}if(y.Q.b(d)){x=A.aZR(d,this,e)
break $label0$0}x=this.U2(d,e)
break $label0$0}return x},
eJ(d,e){var x
$label0$0:{if(d instanceof D.aK){x=A.aZR(this,A.bR4(d),e)
break $label0$0}if(y.Q.b(d)){x=A.aZR(this,d,e)
break $label0$0}x=this.U3(d,e)
break $label0$0}return x},
l(d,e){var x=this
if(e==null)return!1
if(x===e)return!0
if(J.as(e)!==B.Y(x))return!1
return e instanceof A.nA&&J.p(e.a,x.a)&&J.p(e.b,x.b)&&J.p(e.c,x.c)&&B.ec(e.d,x.d)&&e.e.l(0,x.e)},
gt(d){var x=this,w=x.d
w=w==null?null:B.dj(w)
return B.ar(x.a,x.b,x.c,x.e,w,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a,C.a)},
a1i(d,e,f){return this.e.fS(new B.R(0,0,0+d.a,0+d.b),f).p(0,e)},
Ap(d){return new A.apu(this,d)}}
A.apu.prototype={
aQQ(d,e){var x,w,v,u=this
if(d.l(0,u.c)&&e==u.d)return
if(u.r==null){x=u.b
x=x.a!=null||x.b!=null}else x=!1
if(x){x=$.aC().aF()
u.r=x
w=u.b.a
if(w!=null)x.sau(0,w)}x=u.b
w=x.b
if(w!=null){v=u.r
v.toString
v.se5(w.ws(0,d,e))}w=x.d
if(w!=null){if(u.w==null){u.w=w.length
u.z=B.a1(new B.ae(w,new A.bvU(),B.ag(w).i("ae<1,tw>")),!0,y.Z)}if(x.e.gkh())u.x=B.a1(new B.ae(w,new A.bvV(d),B.ag(w).i("ae<1,R>")),!0,y.A)
else u.y=B.a1(new B.ae(w,new A.bvW(u,d,e),B.ag(w).i("ae<1,aac>")),!0,y.i)}w=x.e
if(!w.gkh())v=u.r!=null||u.w!=null
else v=!1
if(v)u.e=w.fS(d,e)
if(x.c!=null)u.f=w.ls(d,e)
u.c=d
u.d=e},
aTW(d,e,f){var x,w,v,u,t=this
if(t.w!=null){x=t.b.e
if(x.gkh()){w=0
while(!0){v=t.w
v.toString
if(!(w<v))break
v=t.x
v===$&&B.b()
v=v[w]
u=t.z
u===$&&B.b()
x.ln(d,v,u[w],f);++w}}else{w=0
while(!0){x=t.w
x.toString
if(!(w<x))break
x=t.y
x===$&&B.b()
x=x[w]
v=t.z
v===$&&B.b()
d.fI(x,v[w]);++w}}}},
aPx(d,e){var x,w=this,v=w.b.c
if(v==null)return
x=w.Q
if(x==null){x=w.a
x.toString
x=w.Q=v.Gh(x)
v=x}else v=x
x=w.c
x.toString
v.xs(d,x,w.f,e)},
m(){var x=this.Q
if(x!=null)x.m()
this.a4Y()},
kL(d,e,f){var x=this,w=f.e,v=e.a,u=e.b,t=new B.R(v,u,v+w.a,u+w.b),s=f.d
x.aQQ(t,s)
x.aTW(d,t,s)
if(x.r!=null){w=x.b.e
v=w.gkh()
u=x.r
if(v){u.toString
w.ln(d,t,u,s)}else{w=x.e
w===$&&B.b()
u.toString
d.fI(w,u)}}x.aPx(d,f)
x.b.e.iz(d,t,s)}}
A.Aj.prototype={
X(){return new A.UV(new G.bm(null,y.z))}}
A.UV.prototype={
a6(){this.aE()
$.d1.k2$.push(new A.beU(this))
$.ah.ac$.d.a.f.A(0,this.gaaa())},
m(){$.ah.ac$.d.a.f.F(0,this.gaaa())
this.aP()},
afM(d){this.Mx(new A.beS(this))},
aIH(d){if(this.c==null)return
this.afM(d)},
azj(d){if(!this.e)this.Mx(new A.beN(this))},
azl(d){if(this.e)this.Mx(new A.beO(this))},
azh(d){var x,w=this
if(w.f!==d){w.Mx(new A.beM(w,d))
x=w.a.Q
if(x!=null)x.$1(w.f)}},
abA(d,e){var x,w,v,u,t,s,r=this,q=new A.beR(r),p=new A.beQ(r,new A.beP(r))
if(d==null){x=r.a
x.toString
w=x}else w=d
v=q.$1(w)
u=p.$1(w)
if(e!=null)e.$0()
x=r.a
x.toString
t=q.$1(x)
x=r.a
x.toString
s=p.$1(x)
if(u!==s)r.a.y.$1(s)
if(v!==t){q=r.a.z
if(q!=null)q.$1(t)}},
Mx(d){return this.abA(null,d)},
aNr(d){return this.abA(d,null)},
bd(d){this.bF(d)
if(this.a.c!==d.c)$.d1.k2$.push(new A.beT(this,d))},
gazf(){var x,w=this.c
w.toString
w=B.dD(w,C.hX)
x=w==null?null:w.ch
$label0$0:{if(C.hm===x||x==null){w=this.a.c
break $label0$0}if(F.lu===x){w=!0
break $label0$0}w=null}return w},
G(d){var x,w,v,u=this,t=null,s=u.a,r=s.as
s=s.d
x=u.gazf()
w=u.a
v=H.mr(B.rY(!1,x,w.ax,t,!0,!0,s,!0,t,u.gazg(),t,t,t,t),r,u.r,u.gazi(),u.gazk(),t)
s=w.c
if(s){r=w.w
r=r!=null&&r.a!==0}else r=!1
if(r){r=w.w
r.toString
v=D.z5(r,v)}if(s){s=w.x
s=s!=null&&s.gce(s)}else s=!1
if(s){s=u.a.x
s.toString
v=A.ad8(v,t,s)}return v}}
A.yf.prototype={}
A.Hv.prototype={
srP(d){var x=this
if(!D.aum(x.b,d)){x.b=d
x.c=null
x.ad()}},
gaaN(){var x=this.c
return x==null?this.c=A.caa(this.b):x},
aFw(d,e){var x,w,v,u=this.gaaN().h(0,d.b)
if(u==null)u=B.a([],y.D)
u=B.a1(u,!0,y.M)
x=this.gaaN().h(0,null)
C.b.H(u,x==null?B.a([],y.D):x)
x=u.length
w=0
for(;w<u.length;u.length===x||(0,B.a5)(u),++w){v=u[w]
if(v.a.aXI(d,e))return v.b}return null},
b2X(d,e){var x,w,v,u,t,s,r,q,p=$.i1.oX$
p===$&&B.b()
x=this.aFw(e,p)
if(x!=null){p=$.ah.ac$.d.c
w=p==null?null:p.e
if(w!=null){v=D.bF3(w,x,y.H)
if(v!=null){w.an(y.a)
p=B.bF1(w)
u=p.b4H(v,x,w)
t=u.a
s=null
r=u.b
s=r
q=t
if(q)return v.a3g(x,s)}}}return C.iu},
$iaz:1}
A.Ce.prototype={
grP(){var x=this.c
return x==null?this.d:x.b},
X(){return new A.Xo()}}
A.Xo.prototype={
m(){var x=this.d
if(x!=null){x.M$=$.an()
x.R$=0}this.aP()},
a6(){var x,w
this.aE()
x=this.a
if(x.c==null){w=new A.Hv(E.lq,$.an())
this.d=w
w.srP(x.grP())}},
bd(d){var x,w,v=this
v.bF(d)
x=v.a
w=x.c
if(w!=d.c)if(w!=null){w=v.d
if(w!=null){w.M$=$.an()
w.R$=0}v.d=null}else if(v.d==null)v.d=new A.Hv(E.lq,$.an())
w=v.d
if(w!=null)w.srP(x.grP())},
aJo(d,e){var x,w=d.e
if(w==null)return C.iu
x=this.a.c
if(x==null){x=this.d
x.toString}return x.b2X(w,e)},
G(d){var x=null,w=E.anP.k(0)
return B.rY(!1,!1,this.a.e,w,x,x,x,!0,x,x,x,this.gaJn(),x,x)}}
A.apx.prototype={}
A.apy.prototype={}
A.arW.prototype={}
A.HF.prototype={}
A.lG.prototype={
e6(d){var x=B.B(this),w=y.h
return new A.RR(B.J(x.i("lG.0"),w),B.J(y.B,w),this,C.aR,x.i("RR<lG.0,lG.1>"))}}
A.qG.prototype={
glP(d){return this.fA$.gbc(0)},
jy(){J.ds(this.glP(this),this.ga2U())},
ca(d){J.ds(this.glP(this),d)},
Nw(d,e){var x=this.fA$,w=x.h(0,e)
if(w!=null){this.qD(w)
x.F(0,e)}if(d!=null){x.n(0,e,d)
this.l9(d)}}}
A.RR.prototype={
ga8(){return this.$ti.i("qG<1,2>").a(B.c4.prototype.ga8.call(this))},
ca(d){this.p1.gbc(0).ae(0,d)},
li(d){this.p1.F(0,d.c)
this.mi(d)},
ix(d,e){this.vd(d,e)
this.afy()},
em(d,e){this.pS(0,e)
this.afy()},
afy(){var x,w,v,u,t,s,r,q,p,o,n,m,l,k=this,j=k.e
j.toString
x=k.$ti
x.i("lG<1,2>").a(j)
w=k.p2
v=y.h
k.p2=B.J(y.B,v)
u=k.p1
x=x.c
k.p1=B.J(x,v)
for(v=j.ga4O(),t=v.length,s=0;s<t;++s){r=v[s]
q=j.ahm(r)
p=q==null?null:q.a
o=u.h(0,r)
n=w.h(0,p)
if(n!=null)m=u.F(0,x.a(n.c))
else m=(o==null?null:o.gbH().a)==null?u.F(0,r):null
l=k.f8(m,q,r)
if(l!=null){k.p1.n(0,r,l)
if(p!=null)k.p2.n(0,p,l)}}u.gbc(0).ae(0,k.gb0i())},
m6(d,e){this.$ti.i("qG<1,2>").a(B.c4.prototype.ga8.call(this)).Nw(d,e)},
n5(d,e){var x=this.$ti.i("qG<1,2>")
if(x.a(B.c4.prototype.ga8.call(this)).fA$.h(0,e)===d)x.a(B.c4.prototype.ga8.call(this)).Nw(null,e)},
m8(d,e,f){var x=this.$ti.i("qG<1,2>").a(B.c4.prototype.ga8.call(this))
if(x.fA$.h(0,e)===d)x.Nw(null,e)
x.Nw(d,f)}}
A.Xx.prototype={
bi(d,e){return this.a5u(d,e)}}
var z=a.updateTypes(["y(Aj)","tw(bG)","R(bG)","aac(bG)","~(pW)","~(lz)","~(lA)","~(y)","~(Cd,bw)","A<yf>()","na(fb,lt)","U?(U?,U?,H)"])
A.bvU.prototype={
$1(d){return d.kS()},
$S:z+1}
A.bvV.prototype={
$1(d){return this.a.eQ(d.b).fk(d.d)},
$S:z+2}
A.bvW.prototype={
$1(d){return this.a.b.e.fS(this.b.eQ(d.b).fk(d.d),this.c)},
$S:z+3}
A.beU.prototype={
$1(d){var x=$.ah.ac$.d.a.b
if(x==null)x=B.CY()
this.a.afM(x)},
$S:3}
A.beS.prototype={
$0(){var x=$.ah.ac$.d.a.b
switch((x==null?B.CY():x).a){case 0:x=!1
break
case 1:x=!0
break
default:x=null}this.a.d=x},
$S:0}
A.beN.prototype={
$0(){this.a.e=!0},
$S:0}
A.beO.prototype={
$0(){this.a.e=!1},
$S:0}
A.beM.prototype={
$0(){this.a.f=this.b},
$S:0}
A.beR.prototype={
$1(d){var x=this.a
return x.e&&d.c&&x.d},
$S:z+0}
A.beP.prototype={
$1(d){var x,w=this.a.c
w.toString
w=B.dD(w,C.hX)
x=w==null?null:w.ch
$label0$0:{if(C.hm===x||x==null){w=d.c
break $label0$0}if(F.lu===x){w=!0
break $label0$0}w=null}return w},
$S:z+0}
A.beQ.prototype={
$1(d){var x=this.a
return x.f&&x.d&&this.b.$1(d)},
$S:z+0}
A.beT.prototype={
$1(d){this.a.aNr(this.b)},
$S:3}
A.aZY.prototype={
$2(d,e){var x=d.gbaL(),w=this.a,v=0
for(;v<1;++v)J.c7(w.cR(0,x[v],new A.aZX()),new A.yf(d,e))},
$S:z+8}
A.aZX.prototype={
$0(){return B.a([],y.D)},
$S:z+9};(function installTearOffs(){var x=a._instance_1u,w=a._instance_2u,v=a.installStaticTearOff
var u
x(u=A.UV.prototype,"gaaa","aIH",4)
x(u,"gazi","azj",5)
x(u,"gazk","azl",6)
x(u,"gazg","azh",7)
w(A.Xo.prototype,"gaJn","aJo",10)
v(A,"a_5",3,null,["$3"],["RD"],11,0)})();(function inheritance(){var x=a.mixin,w=a.mixinHard,v=a.inheritMany,u=a.inherit
v(B.u,[A.eu,A.arW,A.apx,A.lG,A.qG])
u(A.nA,D.ku)
u(A.apu,D.Ec)
v(B.cw,[A.bvU,A.bvV,A.bvW,A.beU,A.beR,A.beP,A.beQ,A.beT])
v(B.W,[A.Aj,A.Ce])
v(B.a2,[A.UV,A.Xo])
v(B.cR,[A.beS,A.beN,A.beO,A.beM,A.aZX])
u(A.yf,A.arW)
u(A.apy,A.apx)
u(A.Hv,A.apy)
u(A.aZY,B.dt)
u(A.Xx,B.b_)
u(A.HF,A.Xx)
u(A.RR,B.c4)
x(A.apx,B.b9)
x(A.apy,B.bg)
x(A.arW,B.b9)
w(A.Xx,A.lG)})()
B.ch(b.typeUniverse,JSON.parse('{"nA":{"ku":[]},"Aj":{"W":[],"o":[]},"UV":{"a2":["Aj"]},"Ce":{"W":[],"o":[]},"Hv":{"bg":[],"az":[]},"Xo":{"a2":["Ce"]},"HF":{"lG":["1","2"],"b_":[],"o":[]},"RR":{"c4":[],"bJ":[],"G":[]}}'))
B.hK(b.typeUniverse,JSON.parse('{"HF":2,"Xx":2}'))
var y=(function rtii(){var x=B.C
return{h:x("bJ"),H:x("bw"),D:x("r<yf>"),B:x("h8"),z:x("bm<a2<W>>"),T:x("A<yf>"),Z:x("tw"),i:x("aac"),A:x("R"),a:x("ur"),M:x("yf"),I:x("z?"),Q:x("nA?")}})();(function constants(){E.rx=new B.a0H(2,"outer")
E.rD=new D.eZ(F.u,F.u,F.u,F.u)
E.Gt=new B.t(0,4)
E.Rr=new B.x(167772160)
E.ni=new B.x(452984831)
E.iN=new B.t(0,2)
E.oW=new B.t(0,8)
E.lq=new B.aN(C.cg,[],B.C("aN<Cd,bw>"))
E.anP=B.bF("Ce")})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_86",e:"endPart",h:b})})($__dart_deferred_initializers__,"cEDRpyCzdVHsqxoGhBgbKNNMiKk=");