package io.github.korzepadawid.springtaskplanning.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "avatars")
public class Avatar extends AbstractBaseEntity {

  @OneToOne private User user;

  @NotBlank
  @Size(min = 0, max = 36)
  private String uuid;

  @Lob
  @Type(type = "org.hibernate.type.BinaryType")
  private Byte[] photo;

  @Embedded private DateAudit dateAudit = new DateAudit();

  public Avatar() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(Byte[] photo) {
    this.photo = photo;
  }

  public DateAudit getDateAudit() {
    return dateAudit;
  }

  public void setDateAudit(DateAudit dateAudit) {
    this.dateAudit = dateAudit;
  }
}
