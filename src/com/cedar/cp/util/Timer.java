// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util;

import com.cedar.cp.util.Log;
import java.io.Serializable;

public class Timer implements Serializable {

   private Log _log;
   private long _startTime;
   private long _cumulativeTime;
   private boolean _started;
   public static final int FORMAT_HHMMSSmmm = 1;
   public static final int FORMAT_HHMMSS = 2;
   public static final int FORMAT_HHMM = 3;
   public static final int FORMAT_TRIMMED = 4;


   public Timer(Log log_) {
      this(log_, true);
   }

   public Timer() {
      this((Log)null, true);
   }

   public Timer(Log log_, boolean startIt_) {
      this._log = null;
      this._startTime = 0L;
      this._cumulativeTime = 0L;
      this._started = false;
      this._log = log_;
      if(startIt_) {
         this.start();
      }

   }

   public void start() {
      if(!this._started) {
         this._started = true;
         this._startTime = System.currentTimeMillis();
      }

   }

   public long stop() {
      if(this._started) {
         long stopTime = System.currentTimeMillis();
         this._cumulativeTime += stopTime - this._startTime;
         this._started = false;
      }

      return this._cumulativeTime;
   }

   public boolean isRunning() {
      return this._started;
   }

   public void logInfo(String sourceMethod_, Object text_) {
      this.logInfo(4, sourceMethod_, text_);
   }

   public void logInfo(int format_, String sourceMethod_, Object text_) {
      if(this._log != null) {
         this._log.info(sourceMethod_, "[timer=" + this.getElapsed(format_) + "] " + text_);
      } else {
         System.out.println(sourceMethod_ + " [timer=" + this.getElapsed(format_) + "] " + text_ + this.getElapsed(format_));
      }

   }

   public void logDebug(String sourceMethod_, Object text_) {
      this.logDebug(4, sourceMethod_, text_);
   }

   public void logDebug(int format_, String sourceMethod_, Object text_) {
      if(this._log != null) {
         this._log.debug(sourceMethod_, "[timer=" + this.getElapsed(format_) + "] " + text_);
      } else {
         System.out.println(sourceMethod_ + " [timer=" + this.getElapsed(format_) + "] " + text_ + this.getElapsed(format_));
      }

   }

   public void logDebugx() {
      if(this._log != null) {
         this._log.debug("[timer=" + this.getElapsed(4) + ']');
      } else {
         System.out.println("[timer=" + this.getElapsed(4) + ']');
      }

   }

   public void logDebug(String text_) {
      if(this._log != null) {
         this._log.debug("[timer=" + this.getElapsed(4) + "] " + text_);
      } else {
         System.out.println("[timer=" + this.getElapsed(4) + "] " + text_);
      }

   }

   public void reset() {
      this._startTime = 0L;
      this._cumulativeTime = 0L;
      this._started = false;
      this.start();
   }

   public String getElapsed(int format_) {
      long stopTime = System.currentTimeMillis();
      long cumulativeTime = this._cumulativeTime + (stopTime - this._startTime);
      return getFormatted(cumulativeTime, format_);
   }

   public String getElapsed() {
      long stopTime = System.currentTimeMillis();
      long cumulativeTime = this._cumulativeTime + (stopTime - this._startTime);
      return getFormatted(cumulativeTime, 4);
   }

   public long getStartTime() {
      return this._startTime;
   }

   public static String getFormatted(long parmMillis_, int format_) {
      long millis = parmMillis_;
      if(parmMillis_ < 0L) {
         millis = parmMillis_ * -1L;
      }

      long secs = millis / 1000L;
      millis -= secs * 1000L;
      if(format_ != 1 && format_ != 4 && millis > 499L) {
         ++secs;
      }

      long mins = secs / 60L;
      secs -= mins * 60L;
      if(format_ == 3 && secs > 29L) {
         ++mins;
      }

      long hours = mins / 60L;
      mins -= hours * 60L;
      StringBuffer sb = new StringBuffer();
      if(hours < 100L) {
         sb.append(("" + (100L + hours)).substring(1));
      } else {
         sb.append("" + hours);
      }

      sb.append(":" + ("" + (100L + mins)).substring(1));
      if(format_ == 3) {
         return sb.toString();
      } else {
         sb.append(":" + ("" + (100L + secs)).substring(1));
         if(format_ == 2) {
            return sb.toString();
         } else {
            sb.append("." + String.valueOf(1000L + millis).substring(1));
            if(format_ == 4 && hours == 0L) {
               sb.delete(0, 3);
               if(mins == 0L) {
                  sb.delete(0, 3);
                  if(secs < 10L) {
                     sb.delete(0, 1);
                  }
               }
            }

            if(parmMillis_ < 0L) {
               sb.insert(0, '-');
            }

            return sb.toString();
         }
      }
   }
}
