package org.hypergraphdb.app.sail.model;

import org.hypergraphdb.HyperGraph;
import org.hypergraphdb.app.sail.HyperValueFactory;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * created Feb 1, 2010  - 10:17:14 AM
 *
 * @author IanHolsman
 *         Copyright (C) 2010 by Aol. All Rights Reserved.
 */
public class HyperContextStatement implements Statement {
  public HResource subject;
  public HURI predicate;
  public HValue object;
  public HyperContext context;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof HyperContextStatement)) return false;

    HyperContextStatement that = (HyperContextStatement) o;

    /* NULL context matches any context */
    if ( context != null)  {
      if (that.context != null) {
         if (!context.equals(that.context)) {
           return false;
         }
      }
    }
  //  if (context != null ? !context.equals(that.context) : that.context != null) return false;
    if (!object.equals(that.object)) return false;
    if (!predicate.equals(that.predicate)) return false;
    if (!subject.equals(that.subject)) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = subject.hashCode();
    result = 31 * result + predicate.hashCode();
    result = 31 * result + object.hashCode();
    return result;
  }

  public HyperContextStatement() {

  }

  public HyperContextStatement(Resource subject, URI predicate, Value object, Resource context) {
    this.setSubject(subject);
    this.setPredicate(predicate);
    this.setObject(object);
    this.setContext(context);
  }

  public HyperContextStatement(HyperGraph graph, HyperStatementLink link) {
    this.setSubject(graph.<HResource>get(link.getSubjectH()));
    this.setPredicate(graph.<HURI>get(link.getPredicateH()));
    this.setObject(graph.<HValue>get(link.getObjectH()));
    if (link.getContextH() != null) {
      this.setContext(graph.<HyperContext>get(link.getContextH()));
    } else {
      this.setContext(null);
    }
  }

  public Resource getSubject() {
    return subject;
  }

  public URI getPredicate() {
    return predicate;
  }

  public void setSubject(Resource subject) {
    /*
    if (subject instanceof HResourceI) {
      this.subject = (HResourceI) subject;
    } else {
      if (subject instanceof URI) {
        this.subject = new HURI( (URI)subject);
      }  else if ( subject instanceof BNode) {
        this.subject = new HBNode( (BNode)subject);
      } else {
        this.subject = new HResource(subject);
      }
    } */
    this.subject = HyperValueFactory.fixResource(subject);
  }

  public void setPredicate(URI predicate) {

    this.predicate = new HURI(predicate);
  }

  public void setObject(Value object) {

    this.object = HyperValueFactory.fixValue(object);
    /*
    if (object instanceof HValue) {
      this.object = (HValue) object;
    } else {
      this.object = new HValue(object);
    }
    */
  }

  public void setContext(Resource context) {
    if (context == null) {
      this.context = null;
      return;

    }
    if (context.stringValue() == null) {
      this.context = null;
      return;
    }

    if (context instanceof HyperContext) {
      this.context = (HyperContext) context;
    } else {
      this.context = new HyperContext(context);
    }
  }

  public Value getObject() {
    return object;
  }

  public HyperContext getContext() {
    return context;
  }
}
