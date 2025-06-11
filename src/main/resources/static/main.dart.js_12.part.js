((a,b,c)=>{a[b]=a[b]||{}
a[b][c]=a[b][c]||[]
a[b][c].push({p:"main.dart.js_12",e:"beginPart"})})(self,"$__dart_deferred_initializers__","eventLog")
$__dart_deferred_initializers__.current=function(a,b,c,$){var J,A,B,C={ajj:function ajj(d){this.a=0
this.b=d},aiK:function aiK(d){this.a=0
this.b=d}}
J=c[1]
A=c[0]
B=c[2]
C=a.updateHolder(c[109],C)
C.ajj.prototype={
A(d,e){var x,w,v=this,u=J.M(e),t=u.gB(e)
if(t===0)return
x=v.a+t
if(v.b.length<x)v.az7(x)
if(y.c.b(e))B.q.bW(v.b,v.a,x,e)
else for(w=0;w<t;++w)v.b[v.a+w]=u.h(e,w)
v.a=x},
az7(d){var x,w,v,u=d*2
if(u<1024)u=1024
else{x=u-1
x|=B.e.bp(x,1)
x|=x>>>2
x|=x>>>4
x|=x>>>8
u=((x|x>>>16)>>>0)+1}w=new Uint8Array(u)
v=this.b
B.q.bW(w,0,v.length,v)
this.b=w},
a39(){var x,w,v=this,u=v.a
if(u===0)return $.DF()
x=v.b
w=A.eG(x.buffer,x.byteOffset,u)
v.a=0
v.b=$.DF()
return w},
BJ(){var x,w=this.a
if(w===0)return $.DF()
x=this.b
return new Uint8Array(A.hi(A.eG(x.buffer,x.byteOffset,w)))},
gB(d){return this.a},
gam(d){return this.a===0},
gce(d){return this.a!==0},
O(d){this.a=0
this.b=$.DF()}}
C.aiK.prototype={
A(d,e){var x=y.c.b(e)?e:new Uint8Array(A.hi(e))
this.b.push(x)
this.a=this.a+x.length},
a39(){var x,w,v,u,t,s,r,q=this,p=q.a
if(p===0)return $.DF()
x=q.b
w=x.length
if(w===1){v=x[0]
q.a=0
B.b.O(x)
return v}v=new Uint8Array(p)
for(u=0,t=0;t<x.length;x.length===w||(0,A.a5)(x),++t,u=r){s=x[t]
r=u+s.length
B.q.bW(v,u,r,s)}q.a=0
B.b.O(x)
return v},
BJ(){var x,w,v,u,t,s,r=this.a
if(r===0)return $.DF()
x=new Uint8Array(r)
for(r=this.b,w=r.length,v=0,u=0;u<r.length;r.length===w||(0,A.a5)(r),++u,v=s){t=r[u]
s=v+t.length
B.q.bW(x,v,s,t)}return x},
gB(d){return this.a},
gam(d){return this.a===0},
gce(d){return this.a!==0},
O(d){this.a=0
B.b.O(this.b)}}
var z=a.updateTypes([]);(function inheritance(){var x=a.inheritMany
x(A.u,[C.ajj,C.aiK])})()
var y={c:A.C("cq")};(function lazyInitializers(){var x=a.lazyFinal
x($,"crz","DF",()=>A.Pr(0))})()};
((a,b)=>{a[b]=a.current
a.eventLog.push({p:"main.dart.js_12",e:"endPart",h:b})})($__dart_deferred_initializers__,"fFv8tU7czq6fTlMSx8Zpj1v6res=");