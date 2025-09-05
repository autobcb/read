((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var B,C,A={baT:function baT(d,e,f,g){var _=this
_.a=d
_.b=e
_.c=f
_.d=g},baU:function baU(){},baV:function baV(d,e,f,g,h,i){var _=this
_.a=d
_.b=e
_.c=f
_.d=g
_.e=h
_.f=i},baS:function baS(){},Xk:function Xk(d,e,f,g){var _=this
_.a=d
_.b=e
_.c=f
_.d=g},Lv:function Lv(d,e,f){var _=this
_.b=_.w=null
_.c=!1
_.Dk$=d
_.dQ$=e
_.aJ$=f
_.a=null},aiL:function aiL(d,e,f,g,h,i,j){var _=this
_.aw=d
_.y1=e
_.y2=f
_.d3$=g
_.an$=h
_.dj$=i
_.b=_.dy=null
_.c=0
_.y=_.d=null
_.z=!0
_.Q=null
_.as=!1
_.at=null
_.ay=$
_.ch=j
_.CW=!1
_.cx=$
_.cy=!0
_.db=!1
_.dx=$},
bXC(d,e,f,g,h,i){var x=null,w=d==null
w=w?H.iD:x
return new A.IP(e,new F.Fp(f,g,!0,!0,!0,D.bV1(),x),i,C.I,!1,d,x,w,!1,x,g,E.q,x,x,C.x,E.aT,h)},
IP:function IP(d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t){var _=this
_.rx=d
_.ry=e
_.db=f
_.c=g
_.d=h
_.e=i
_.f=j
_.r=k
_.x=l
_.Q=m
_.as=n
_.ax=o
_.ay=p
_.ch=q
_.CW=r
_.cx=s
_.a=t},
al1:function al1(d,e,f){this.f=d
this.d=e
this.a=f}},F,G,E,D,H
B=c[0]
C=c[2]
A=a.updateHolder(c[39],A)
F=c[73]
G=c[108]
E=c[117]
D=c[79]
H=c[127]
A.baT.prototype={
axF(d){var x=this.c
return d.IF(this.d,x,x)},
j(d){var x=this
return"SliverGridGeometry("+C.b.c1(B.a(["scrollOffset: "+B.m(x.a),"crossAxisOffset: "+B.m(x.b),"mainAxisExtent: "+B.m(x.c),"crossAxisExtent: "+B.m(x.d)],y.x),", ")+")"}}
A.baU.prototype={}
A.baV.prototype={
axY(d){var x=this.b
if(x>0)return Math.max(0,this.a*C.e.iS(d/x)-1)
return 0},
aQ1(d){var x,w,v=this
if(v.f){x=v.c
w=v.e
return v.a*x-d-w-(x-w)}return d},
WO(d){var x=this,w=x.a,v=C.f.aP(d,w)
return new A.baT(C.f.iN(d,w)*x.b,x.aQ1(v*x.c),x.d,x.e)},
aoa(d){var x
if(d===0)return 0
x=this.b
return x*(C.f.iN(d-1,this.a)+1)-(x-this.d)}}
A.baS.prototype={}
A.Xk.prototype={
a8x(d){var x=this,w=x.c,v=x.a,u=Math.max(0,d.w-w*(v-1))/v,t=u/x.d
return new A.baV(v,t+x.b,u+w,t,u,G.OC(d.x))}}
A.Lv.prototype={
j(d){return"crossAxisOffset="+B.m(this.w)+"; "+this.aDM(0)}}
A.aiL.prototype={
hk(d){if(!(d.b instanceof A.Lv))d.b=new A.Lv(!1,null,null)},
sayt(d){var x,w,v=this
if(v.aw===d)return
x=!0
if(B.Z(d)===B.Z(v.aw)){w=v.aw
if(w.a===d.a)if(w.b===d.b)if(w.c===d.c)x=w.d!==d.d}if(x)v.ak()
v.aw=d},
CC(d){var x=d.b
x.toString
x=y.t.a(x).w
x.toString
return x},
ce(){var x,w,v,u,t,s,r,q,p,o,n,m,l,k,j,i,h,g,f,e,d,a0,a1,a2,a3,a4,a5,a6=this,a7=null,a8=y.z.a(B.P.prototype.gad.call(a6)),a9=a6.y1
a9.R8=!1
x=a8.d
w=x+a8.z
v=w+a8.Q
u=a6.aw.a8x(a8)
t=u.b
s=t>1e-10?u.a*C.e.iN(w,t):0
r=isFinite(v)?u.axY(v):a7
if(a6.an$!=null){q=a6.anI(s)
a6.y6(q,r!=null?a6.anJ(r):0)}else a6.y6(0,0)
p=u.WO(s)
if(a6.an$==null)if(!a6.a2B(s,p.a)){o=u.aoa(a9.gCB())
a6.dy=D.pl(a7,!1,a7,a7,o,0,0,o,a7)
a9.yk()
return}n=p.a
m=n+p.c
t=a6.an$
t.toString
t=t.b
t.toString
l=y.c
t=l.a(t).b
t.toString
k=t-1
t=y.t
j=a7
for(;k>=s;--k){i=u.WO(k)
h=i.c
g=a6.ar9(a8.IF(i.d,h,h))
f=g.b
f.toString
t.a(f)
e=i.a
f.a=e
f.w=i.b
if(j==null)j=g
m=Math.max(m,e+h)}if(j==null){h=a6.an$
h.toString
h.iY(p.axF(a8))
j=a6.an$
h=j.b
h.toString
t.a(h)
h.a=n
h.w=p.b}h=j.b
h.toString
h=l.a(h).b
h.toString
k=h+1
h=B.B(a6).i("aK.1")
f=r!=null
while(!0){if(!(!f||k<=r)){d=!1
break}i=u.WO(k)
e=i.c
a0=a8.IF(i.d,e,e)
a1=j.b
a1.toString
g=h.a(a1).aJ$
if(g!=null){a1=g.b
a1.toString
a1=l.a(a1).b
a1.toString
a1=a1!==k}else a1=!0
if(a1){g=a6.ar7(a0,j)
if(g==null){d=!0
break}}else g.iY(a0)
a1=g.b
a1.toString
t.a(a1)
a2=i.a
a1.a=a2
a1.w=i.b
m=Math.max(m,a2+e);++k
j=g}t=a6.dj$
t.toString
t=t.b
t.toString
t=l.a(t).b
t.toString
a3=d?m:a9.a4y(a8,s,t,n,m)
a4=a6.IO(a8,Math.min(x,n),m)
a5=a6.Sg(a8,n,m)
a6.dy=D.pl(a5,a3>a4||x>0||a8.f!==0,a7,a7,a3,a4,0,a3,a7)
if(a3===m)a9.R8=!0
a9.yk()}}
A.IP.prototype={
anu(d){return new A.al1(this.rx,this.ry,null)}}
A.al1.prototype={
bi(d){var x=new A.aiL(this.f,y.v.a(d),B.E(y.e,y.g),0,null,null,B.aU())
x.bh()
return x},
bq(d,e){e.sayt(this.f)},
a4x(d,e,f,g,h){var x,w
this.aDN(d,e,f,g,h)
x=this.f.a8x(d)
w=this.d.gD8()
w.toString
w=x.aoa(w)
return w}}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany,w=a.inherit
x(B.w,[A.baT,A.baU,A.baS])
w(A.baV,A.baU)
w(A.Xk,A.baS)
w(A.Lv,D.j2)
w(A.aiL,D.qH)
w(A.IP,D.Ce)
w(A.al1,D.pm)})()
B.cQ(b.typeUniverse,JSON.parse('{"Lv":{"j2":[],"wt":[],"ha":["O"],"qu":[],"eH":[]},"aiL":{"qH":[],"fm":[],"aK":["O","j2"],"P":[],"aZ":[],"aK.1":"j2","aK.0":"O"},"IP":{"aY":[],"n":[]},"al1":{"pm":[],"b0":[],"n":[]}}'))
var y={x:B.D("t<j>"),g:B.D("O"),z:B.D("qR"),t:B.D("Lv"),v:B.D("AE"),c:B.D("j2"),e:B.D("r")}};
(a=>{a["zpOIM1/hIOl3QUlpn6aCgLTZIGM="]=a.current})($__dart_deferred_initializers__);