((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_11",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={apH:function apH(d){this.a=0
this.b=d},YK:function YK(d){this.a=0
this.b=d},
cvA(){throw B.k(B.aL("Platform._environment"))},
cv7(d,e){throw B.k(B.aL("_IOService._dispatch"))},
c6J(d,e,f){var x,w
if(y.c.b(d)&&J.bZ6(C.o.gb5(d))===d.length)return new A.ap2(d,e)
x=f-e
w=new Uint8Array(x)
C.o.dD(w,0,x,d,e)
return new A.ap2(w,0)},
bTQ(){var x=$.ar.h(0,$.cec())
return x==null?null:x},
cvE(){var x=$.cvB
if(x==null)A.cvA()
x.toString
return x},
ap2:function ap2(d,e){this.a=d
this.b=e}}
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[96],A)
A.apH.prototype={
t(d,e){var x,w,v,u,t,s=this,r=J.M(e),q=r.gC(e)
if(q===0)return
x=s.a+q
if(s.b.length<x)s.aGl(x)
if(y.c.b(e))C.o.bX(s.b,s.a,x,e)
else for(w=0;w<q;++w){v=s.b
u=s.a
t=r.h(e,w)
v.$flags&2&&B.au(v)
v[u+w]=t}s.a=x},
aGl(d){var x,w,v,u=d*2
if(u<1024)u=1024
else{x=u-1
x|=C.f.bl(x,1)
x|=x>>>2
x|=x>>>4
x|=x>>>8
u=((x|x>>>16)>>>0)+1}w=new Uint8Array(u)
v=this.b
C.o.bX(w,0,v.length,v)
this.b=w},
DY(){var x,w=this
if(w.a===0)return $.Gx()
x=J.dV(C.o.gb5(w.b),w.b.byteOffset,w.a)
w.a=0
w.b=$.Gx()
return x},
E_(){var x=this
if(x.a===0)return $.Gx()
return new Uint8Array(B.fb(J.dV(C.o.gb5(x.b),x.b.byteOffset,x.a)))},
gC(d){return this.a},
gae(d){return this.a===0},
gci(d){return this.a!==0},
R(d){this.a=0
this.b=$.Gx()}}
A.YK.prototype={
t(d,e){var x=y.c.b(e)?e:new Uint8Array(B.fb(e))
this.b.push(x)
this.a=this.a+x.length},
DY(){var x,w,v,u,t,s,r,q=this,p=q.a
if(p===0)return $.Gx()
x=q.b
w=x.length
if(w===1){v=x[0]
q.a=0
C.b.R(x)
return v}v=new Uint8Array(p)
for(u=0,t=0;t<x.length;x.length===w||(0,B.T)(x),++t,u=r){s=x[t]
r=u+s.length
C.o.bX(v,u,r,s)}q.a=0
C.b.R(x)
return v},
E_(){var x,w,v,u,t,s,r=this.a
if(r===0)return $.Gx()
x=new Uint8Array(r)
for(r=this.b,w=r.length,v=0,u=0;u<r.length;r.length===w||(0,B.T)(r),++u,v=s){t=r[u]
s=v+t.length
C.o.bX(x,v,s,t)}return x},
gC(d){return this.a},
gae(d){return this.a===0},
gci(d){return this.a!==0},
R(d){this.a=0
C.b.R(this.b)}}
A.ap2.prototype={}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(B.w,[A.apH,A.YK,A.ap2])})()
var y={c:B.F("cB")};(function staticFields(){$.cvB=null})();(function lazyInitializers(){var x=a.lazyFinal
x($,"cLi","Gx",()=>B.DL(0))
x($,"cMB","cec",()=>new B.w())})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_11",e:"endPart",h:b})})($__dart_deferred_initializers__,"hrh48idxet2omH2+ZLxMj1j9QCw=");