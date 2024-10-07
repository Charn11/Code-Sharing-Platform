package platform;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "codes")
public class Code {

    @Id
    @Column
    private String id;
    @Column
    private LocalDateTime date;
    @Lob
    @Column
    private String code;
    @Column
    private long time;
    @Column
    private  long views;
    @Column
    private boolean restrictedByBoth;
    @Column
    private boolean restrictedByTime;
    @Column
    private  boolean restrictedByViews;
    @Column
    private Instant start;

    public Code(){
    }

    public Code(String code, long time, long views){
        this.date = LocalDateTime.now();
        this.code = code;
        this.id = UUID.randomUUID().toString();
        if(time>0&&views>0){
            this.restrictedByBoth = true;
            this.start = Instant.now();
        } else if (time>0) {
            this.restrictedByTime = true;
            this.start = Instant.now();
        } else if (views>0) {
            this.restrictedByViews = true;
        }
        this.time = time;
        this.views = views;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getCode() {
        return code;
    }

    public String getId() {
        return id;
    }

    public Instant getStart() {
        return start;
    }

    public boolean isRestrictedByBoth() {
        return restrictedByBoth;
    }

    public boolean isRestrictedByTime() {
        return restrictedByTime;
    }

    public boolean isRestrictedByViews() {
        return restrictedByViews;
    }

    public long getTime() {
        return time;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setStart(Instant start) {
        this.start = start;
    }
}
