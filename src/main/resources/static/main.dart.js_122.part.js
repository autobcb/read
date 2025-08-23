((a,b)=>{a[b]=a[b]||{}})(self,"$__dart_deferred_initializers__")
$__dart_deferred_initializers__.current=function(a,b,c,$){var B,C,A={baB:function baB(d,e,f,g){var _=this
_.a=d
_.b=e
_.c=f
_.d=g},baC:function baC(){},baD:function baD(d,e,f,g,h,i){var _=this
_.a=d
_.b=e
_.c=f
_.d=g
_.e=h
_.f=i},baA:function baA(){},Xi:function Xi(d,e,f,g){var _=this
_.a=d
_.b=e
_.c=f
_.d=g},Lt:function Lt(d,e,f){var _=this
_.b=_.w=null
_.c=!1
_.Di$=d
_.dP$=e
_.aJ$=f
_.a=null},aiF:function aiF(d,e,f,g,h,i,j){var _=this
_.aw=d
_.y1=e
_.y2=f
_.d3$=g
_.an$=h
_.di$=i
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
bXj(d,e,f,g,h,i){var x=null,w=d==null
w=w?H.iD:x
return new A.IN(e,new F.Fo(f,g,!0,!0,!0,D.bUM(),x),i,C.I,!1,d,x,w,!1,x,g,E.q,x,x,C.x,E.aT,h)},
IN:function IN(d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t){var _=this
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
akW:function akW(d,e,f){this.f=d
this.d=e
this.a=f}},F,G,E,D,H
B=c[0]
C=c[2]
A=a.updateHolder(c[40],A)
F=c[73]
G=c[108]
E=c[117]
D=c[79]
H=c[127]
A.baB.prototype={
axj(d){var x=this.c
return d.IC(this.d,x,x)},
j(d){var x=this
return"SliverGridGeometry("+C.b.c1(B.a(["scrollOffset: "+B.m(x.a),"crossAxisOffset: "+B.m(x.b),"mainAxisExtent: "+B.m(x.c),"crossAxisExtent: "+B.m(x.d)],y.x),", ")+")"}}
A.baC.prototype={}
A.baD.prototype={
axC(d){var x=this.b
if(x>0)return Math.max(0,this.a*C.e.iR(d/x)-1)
return 0},
aPH(d){var x,w,v=this
if(v.f){x=v.c
w=v.e
return v.a*x-d-w-(x-w)}return d},
WH(d){var x=this,w=x.a,v=C.f.aS(d,w)
return new A.baB(C.f.iL(d,w)*x.b,x.aPH(v*x.c),x.d,x.e)},
ao_(d){var x
if(d===0)return 0
x=this.b
return x*(C.f.iL(d-1,this.a)+1)-(x-this.d)}}
A.baA.prototype={}
A.Xi.prototype={
a8r(d){var x=this,w=x.c,v=x.a,u=Math.max(0,d.w-w*(v-1))/v,t=u/x.d
return new A.baD(v,t+x.b,u+w,t,u,G.OA(d.x))}}
A.Lt.prototype={
j(d){return"crossAxisOffset="+B.m(this.w)+"; "+this.aDr(0)}}
A.aiF.prototype={
hk(d){if(!(d.b instanceof A.Lt))d.b=new A.Lt(!1,null,null)},
say7(d){var x,w,v=this
if(v.aw===d)return
x=!0
if(B.Y(d)===B.Y(v.aw)){w=v.aw
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
u=a6.aw.a8r(a8)
t=u.b
s=t>1e-10?u.a*C.e.iL(w,t):0
r=isFinite(v)?u.axC(v):a7
if(a6.an$!=null){q=a6.anx(s)
a6.y6(q,r!=null?a6.any(r):0)}else a6.y6(0,0)
p=u.WH(s)
if(a6.an$==null)if(!a6.a2u(s,p.a)){o=u.ao_(a9.gCB())
a6.dy=D.pi(a7,!1,a7,a7,o,0,0,o,a7)
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
for(;k>=s;--k){i=u.WH(k)
h=i.c
g=a6.aqZ(a8.IC(i.d,h,h))
f=g.b
f.toString
t.a(f)
e=i.a
f.a=e
f.w=i.b
if(j==null)j=g
m=Math.max(m,e+h)}if(j==null){h=a6.an$
h.toString
h.iX(p.axj(a8))
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
h=B.B(a6).i("aJ.1")
f=r!=null
while(!0){if(!(!f||k<=r)){d=!1
break}i=u.WH(k)
e=i.c
a0=a8.IC(i.d,e,e)
a1=j.b
a1.toString
g=h.a(a1).aJ$
if(g!=null){a1=g.b
a1.toString
a1=l.a(a1).b
a1.toString
a1=a1!==k}else a1=!0
if(a1){g=a6.aqX(a0,j)
if(g==null){d=!0
break}}else g.iX(a0)
a1=g.b
a1.toString
t.a(a1)
a2=i.a
a1.a=a2
a1.w=i.b
m=Math.max(m,a2+e);++k
j=g}t=a6.di$
t.toString
t=t.b
t.toString
t=l.a(t).b
t.toString
a3=d?m:a9.a4t(a8,s,t,n,m)
a4=a6.IL(a8,Math.min(x,n),m)
a5=a6.Sa(a8,n,m)
a6.dy=D.pi(a5,a3>a4||x>0||a8.f!==0,a7,a7,a3,a4,0,a3,a7)
if(a3===m)a9.R8=!0
a9.yk()}}
A.IN.prototype={
anj(d){return new A.akW(this.rx,this.ry,null)}}
A.akW.prototype={
bi(d){var x=new A.aiF(this.f,y.v.a(d),B.E(y.e,y.g),0,null,null,B.aU())
x.bh()
return x},
bq(d,e){e.say7(this.f)},
a4s(d,e,f,g,h){var x,w
this.aDs(d,e,f,g,h)
x=this.f.a8r(d)
w=this.d.gD7()
w.toString
w=x.ao_(w)
return w}}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany,w=a.inherit
x(B.w,[A.baB,A.baC,A.baA])
w(A.baD,A.baC)
w(A.Xi,A.baA)
w(A.Lt,D.j0)
w(A.aiF,D.qF)
w(A.IN,D.Cc)
w(A.akW,D.pj)})()
B.cP(b.typeUniverse,JSON.parse('{"Lt":{"j0":[],"wq":[],"ha":["O"],"qs":[],"eG":[]},"aiF":{"qF":[],"fl":[],"aJ":["O","j0"],"P":[],"aZ":[],"aJ.1":"j0","aJ.0":"O"},"IN":{"aY":[],"n":[]},"akW":{"pj":[],"b0":[],"n":[]}}'))
var y={x:B.D("t<j>"),g:B.D("O"),z:B.D("qP"),t:B.D("Lt"),v:B.D("AB"),c:B.D("j0"),e:B.D("r")}};
(a=>{a["6dz34EPv+E0jBzKzn7y6Y0XsRsM="]=a.current})($__dart_deferred_initializers__);