((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_11",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,B,C,A={apP:function apP(d){this.a=0
this.b=d},YT:function YT(d){this.a=0
this.b=d},
cwn(){throw B.k(B.aM("Platform._environment"))},
cvV(d,e){throw B.k(B.aM("_IOService._dispatch"))},
c7q(d,e,f){var x,w
if(y.c.b(d)&&J.bZM(C.o.gb6(d))===d.length)return new A.apc(d,e)
x=f-e
w=new Uint8Array(x)
C.o.dH(w,0,x,d,e)
return new A.apc(w,0)},
bUq(){var x=$.ar.h(0,$.ceU())
return x==null?null:x},
cwr(){var x=$.cwo
if(x==null)A.cwn()
x.toString
return x},
apc:function apc(d,e){this.a=d
this.b=e}}
J=c[1]
B=c[0]
C=c[2]
A=a.updateHolder(c[96],A)
A.apP.prototype={
t(d,e){var x,w,v,u,t,s=this,r=J.M(e),q=r.gC(e)
if(q===0)return
x=s.a+q
if(s.b.length<x)s.aGt(x)
if(y.c.b(e))C.o.c_(s.b,s.a,x,e)
else for(w=0;w<q;++w){v=s.b
u=s.a
t=r.h(e,w)
v.$flags&2&&B.au(v)
v[u+w]=t}s.a=x},
aGt(d){var x,w,v,u=d*2
if(u<1024)u=1024
else{x=u-1
x|=C.f.bn(x,1)
x|=x>>>2
x|=x>>>4
x|=x>>>8
u=((x|x>>>16)>>>0)+1}w=new Uint8Array(u)
v=this.b
C.o.c_(w,0,v.length,v)
this.b=w},
E0(){var x,w=this
if(w.a===0)return $.GA()
x=J.dV(C.o.gb6(w.b),w.b.byteOffset,w.a)
w.a=0
w.b=$.GA()
return x},
E2(){var x=this
if(x.a===0)return $.GA()
return new Uint8Array(B.fc(J.dV(C.o.gb6(x.b),x.b.byteOffset,x.a)))},
gC(d){return this.a},
gae(d){return this.a===0},
gcj(d){return this.a!==0},
P(d){this.a=0
this.b=$.GA()}}
A.YT.prototype={
t(d,e){var x=y.c.b(e)?e:new Uint8Array(B.fc(e))
this.b.push(x)
this.a=this.a+x.length},
E0(){var x,w,v,u,t,s,r,q=this,p=q.a
if(p===0)return $.GA()
x=q.b
w=x.length
if(w===1){v=x[0]
q.a=0
C.b.P(x)
return v}v=new Uint8Array(p)
for(u=0,t=0;t<x.length;x.length===w||(0,B.T)(x),++t,u=r){s=x[t]
r=u+s.length
C.o.c_(v,u,r,s)}q.a=0
C.b.P(x)
return v},
E2(){var x,w,v,u,t,s,r=this.a
if(r===0)return $.GA()
x=new Uint8Array(r)
for(r=this.b,w=r.length,v=0,u=0;u<r.length;r.length===w||(0,B.T)(r),++u,v=s){t=r[u]
s=v+t.length
C.o.c_(x,v,s,t)}return x},
gC(d){return this.a},
gae(d){return this.a===0},
gcj(d){return this.a!==0},
P(d){this.a=0
C.b.P(this.b)}}
A.apc.prototype={}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(B.w,[A.apP,A.YT,A.apc])})()
var y={c:B.G("cn")};(function staticFields(){$.cwo=null})();(function lazyInitializers(){var x=a.lazyFinal
x($,"cMg","GA",()=>B.DP(0))
x($,"cNz","ceU",()=>new B.w())})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_11",e:"endPart",h:b})})($__dart_deferred_initializers__,"nER73Mljg1lpPHSL9pOklXLfru4=");